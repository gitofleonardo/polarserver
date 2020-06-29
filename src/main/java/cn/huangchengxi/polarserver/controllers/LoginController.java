package cn.huangchengxi.polarserver.controllers;

import cn.huangchengxi.polarserver.config.ServerProperties;
import cn.huangchengxi.polarserver.entities.Role;
import cn.huangchengxi.polarserver.entities.User;
import cn.huangchengxi.polarserver.entities.ValidationCode;
import cn.huangchengxi.polarserver.repos.RoleRepository;
import cn.huangchengxi.polarserver.repos.UserRepository;
import cn.huangchengxi.polarserver.repos.ValidationRepository;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationRepository validationRepository;
    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping("/login-success")
    public String success(){
        JSONObject object=new JSONObject();
        object.put("state","success");
        object.put("time",new Date().toString());
        return object.toJSONString();
    }
    @RequestMapping("/login-failure")
    public String failure(){
        JSONObject object=new JSONObject();
        object.put("state","failure");
        object.put("time",new Date().toString());
        return object.toJSONString();
    }
    @RequestMapping("/")
    public String state(HttpSession session){
        JSONObject object=new JSONObject();
        if (session.getAttribute("state")==null){
            object.put("state","offline");
        }else{
            object.put("state","online");
        }
        object.put("time",new Date().toString());
        return object.toJSONString();
    }
    @RequestMapping(name = "/sign-up",method = RequestMethod.POST)
    public String signUp(@RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("validation") String code){
        JSONObject obj=new JSONObject();
        Optional<ValidationCode> c=validationRepository.findByCode(code);
        if (!c.isPresent() || !email.equals(c.get().getEmail())){
            obj.put("state","failure");
            obj.put("message","Validation code not correct");
            return obj.toJSONString();
        }
        if (!checkPassword(password)){
            obj.put("state","failure");
            obj.put("message","Password formatting error.Are you a hacker?");
            return obj.toJSONString();
        }
        List<Role> roles=new ArrayList<>();
        roles.add(roleRepository.findById(0L).orElse(null));
        User user=new User(email,password,roles);
        userRepository.save(user);

        obj.put("state","success");
        obj.put("message","Account created successfully");
        return obj.toJSONString();
    }
    @Autowired
    private JavaMailSender mailSender;
    //get a validation code
    @RequestMapping("/validation-code")
    public String validationCode(@RequestParam("validation-code") String email){
        JSONObject obj=new JSONObject();
        if (!checkEmail(email)){
            obj.put("state","failure");
            obj.put("message","Email formatting error.Are you a hacker?");
            return obj.toJSONString();
        }
        ValidationCode code=new ValidationCode();
        code.setEmail(email);
        code.setCode(generateValidationCode());
        boolean s=sendCodeMessage(email,code.getCode());
        if (s){
            validationRepository.save(code);
            obj.put("state","success");
            obj.put("message","An email code has been sent to your email");
        }else{
            obj.put("state","failure");
            obj.put("message","Email code sent failed");
        }
        return obj.toJSONString();
    }
    private boolean sendCodeMessage(String email,String code){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(ServerProperties.MY_MAIL);
        message.setTo(email);
        message.setSubject("Your Email Validation Code");
        message.setText("Your Email Validation Code is "+code);
        try {
            mailSender.send(message);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    //check validation code
    @RequestMapping("/check-code")
    public String checkValidationCode(@RequestParam("email") String email,@RequestParam("code") String code){
        Optional<ValidationCode> c=validationRepository.findByCode(code);
        JSONObject obj=new JSONObject();
        if (!c.isPresent() || !c.get().getEmail().equals(email)){
            obj.put("state","failure");
            obj.put("message","Check failed");
            return obj.toJSONString();
        }
        obj.put("state","success");
        obj.put("message","Check succeeded");
        return obj.toJSONString();
    }
    private String generateValidationCode(){
        String code=findAvailableCode();
        Optional<ValidationCode> c=validationRepository.findByCode(code);
        while (c.isPresent()){
            code=findAvailableCode();
            c=validationRepository.findByCode(code);
        }
        return code;
    }
    private String findAvailableCode(){
        Random r=new Random();
        StringBuilder result= new StringBuilder();
        for (int i=0;i<6;i++){
            result.append(r.nextInt());
        }
        return result.toString();
    }
    private boolean checkEmail(String email){
        Pattern pattern=Pattern.compile("^[\\S]+@[\\S]+$");
        Matcher m=pattern.matcher(email);
        return m.find();
    }
    private boolean checkPassword(String password){
        Pattern pattern=Pattern.compile("^[\\w]{10,20}$");
        Matcher m=pattern.matcher(password);
        return m.find();
    }
}
