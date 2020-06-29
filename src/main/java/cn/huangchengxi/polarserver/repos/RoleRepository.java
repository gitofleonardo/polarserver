package cn.huangchengxi.polarserver.repos;

import cn.huangchengxi.polarserver.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findById(Long id);
}
