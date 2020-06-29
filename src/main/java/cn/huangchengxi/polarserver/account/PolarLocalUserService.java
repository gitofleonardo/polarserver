package cn.huangchengxi.polarserver.account;

import cn.huangchengxi.polarserver.entities.User;
import cn.huangchengxi.polarserver.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class PolarLocalUserService implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public User findByUid(Long uid) {
        Optional<User> user=userRepository.findById(uid);
        return user.orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user=userRepository.findByEmail(email);
        return user.orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return findByEmail(s);
    }
}
