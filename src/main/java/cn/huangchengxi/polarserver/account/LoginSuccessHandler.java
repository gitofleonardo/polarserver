package cn.huangchengxi.polarserver.account;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        request.getSession().setAttribute("username",authentication.getPrincipal());
        response.sendRedirect("/login-success");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletRequest.getSession().setAttribute("username",authentication.getPrincipal());
        //httpServletResponse.setHeader("fuck-cookie","duck");
        //httpServletResponse.sendRedirect("/login-success");

        JSONObject object=new JSONObject();
        object.put("state","success");
        object.put("time",new Date().toString());
        httpServletResponse.getWriter().write(object.toJSONString());
    }
}
