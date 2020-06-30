package cn.huangchengxi.polarserver.account;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public LoginAuthenticationFilter(){
        super(new AntPathRequestMatcher("/login","POST"));
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String username=obtainUsername(httpServletRequest);
        String password=obtainPassword(httpServletRequest);
        if (username==null || password==null){
            throw new AuthenticationException("Username or password cannot be null"){};
        }
        Authentication authentication;
        if (formatEmail(username)){
            authentication=new EmailPasswordAuthToken(username,password);
        }else if (formatUid(username)){
            authentication=new UidPasswordAuthToken(username,password);
        }else{
            throw new AuthenticationException("Username password not correct"){};
        }
        return getAuthenticationManager().authenticate(authentication);
    }
    private String obtainUsername(HttpServletRequest request){
        return request.getParameter("username");
    }
    private String obtainPassword(HttpServletRequest request){
        return request.getParameter("password");
    }
    private boolean formatEmail(String username){
        Pattern pattern=Pattern.compile("[\\S]+@[\\S]+");
        Matcher m=pattern.matcher(username);
        return m.find();
    }
    private boolean formatUid(String username){
        Pattern pattern=Pattern.compile("^[0-9]+$");
        Matcher m=pattern.matcher(username);
        return m.find();
    }
}
