package cn.huangchengxi.polarserver.entities;

import cn.huangchengxi.polarserver.account.UserDefaultConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("all")
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue
    Long id;
    @Column(name = "email")
    private String email;
    private String passwd;
    private String nickname;
    private String portraitUrl;
    private Boolean expired;
    private Boolean locked;
    private Boolean enabled;

    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<Role> roles;
    public User(){}
    public User(String email,String password,List<Role> roles){
        this.email=email;
        this.passwd=password;
        this.nickname=email;
        this.portraitUrl= UserDefaultConfig.defaultPortraitUrl;
        expired=false;
        locked=false;
        enabled=true;

        setRoles(roles);
    }
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths=new ArrayList<>();
        List<Role> roles=getRoles();
        for (Role r:roles){
            auths.add(new SimpleGrantedAuthority(r.getName()));
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return passwd;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
