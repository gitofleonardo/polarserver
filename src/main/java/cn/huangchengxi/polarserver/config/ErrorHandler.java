package cn.huangchengxi.polarserver.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ErrorHandler implements ErrorController {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(){
        JSONObject obj=new JSONObject();
        obj.put("state","error");
        obj.put("message","Unknown Exception");
        return obj.toJSONString();
    }
    @RequestMapping("/error")
    @ResponseBody
    public String handleError(){
        JSONObject obj=new JSONObject();
        obj.put("state","error");
        obj.put("message","Unknown Exception");
        return obj.toJSONString();
    }
    @Override
    public String getErrorPath() {
        return null;
    }
}
