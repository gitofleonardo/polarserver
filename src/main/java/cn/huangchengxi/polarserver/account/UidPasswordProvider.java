package cn.huangchengxi.polarserver.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UidPasswordProvider implements AuthenticationProvider {
    @Autowired
    PolarLocalUserService service;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails user=service.findByUid(Long.parseLong((String) authentication.getPrincipal()));
        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.getCredentials());
        if (user==null){
            throw new AuthenticationException("User not exists"){};
        }
        String password=(String)authentication.getCredentials();
        if (!passwordEncoder.matches(password,user.getPassword())){
            authentication.setAuthenticated(false);
            throw new AuthenticationException("Password not correct"){};
        }else{
            authentication.setAuthenticated(true);
        }
        ((UidPasswordAuthToken) authentication).setAuths(user.getAuthorities());
        return authentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == UidPasswordAuthToken.class;
    }
}
