package cn.huangchengxi.polarserver.beans;

import cn.huangchengxi.polarserver.account.*;
import cn.huangchengxi.polarserver.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class Configbeans {
    @Bean
    public SecurityInterceptor securityInterceptor(){
        return new SecurityInterceptor();
    }
    @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }
    @Bean
    public LoginFailureHandler loginFailureHandler(){
        return new LoginFailureHandler();
    }
    @Bean
    public UidPasswordProvider uidPasswordProvider(){
        return new UidPasswordProvider();
    }
    @Bean
    public EmailPasswordProvider emailPasswordProvider(){
        return new EmailPasswordProvider();
    }
    @Bean
    public ProviderManager providerManager(@Autowired UidPasswordProvider uidPasswordProvider, @Autowired EmailPasswordProvider emailPasswordProvider){
        return new ProviderManager(uidPasswordProvider,emailPasswordProvider);
    }
    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter(@Autowired ProviderManager manager){
        LoginAuthenticationFilter filter=new LoginAuthenticationFilter();
        filter.setAuthenticationManager(manager);
        filter.setAuthenticationSuccessHandler(loginSuccessHandler());
        filter.setAuthenticationFailureHandler(loginFailureHandler());
        return filter;
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
