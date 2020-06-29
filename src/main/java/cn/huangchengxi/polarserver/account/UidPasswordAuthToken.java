package cn.huangchengxi.polarserver.account;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class UidPasswordAuthToken implements Authentication {
    private String uid;
    private String password;
    private Collection<? extends GrantedAuthority> auths;
    private Boolean authenticated=false;
    public UidPasswordAuthToken(String uid, String password, List<? extends GrantedAuthority> auths){
        this.uid=uid;
        this.password=password;
        this.auths=auths;
    }
    public UidPasswordAuthToken(String uid, String password){
        this.uid=uid;
        this.password=password;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return auths;
    }

    public void setAuths(Collection<? extends GrantedAuthority> auths) {
        this.auths = auths;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return uid;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.authenticated=b;
    }

    @Override
    public String getName() {
        return null;
    }
}
