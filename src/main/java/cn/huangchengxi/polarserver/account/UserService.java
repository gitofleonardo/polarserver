package cn.huangchengxi.polarserver.account;

import cn.huangchengxi.polarserver.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User saveUser(User user);
    User findByUid(Long uid);
    User findByEmail(String email);
}
