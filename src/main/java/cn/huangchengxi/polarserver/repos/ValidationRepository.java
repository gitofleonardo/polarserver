package cn.huangchengxi.polarserver.repos;

import cn.huangchengxi.polarserver.entities.ValidationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidationRepository extends JpaRepository<ValidationCode,Long> {
    Optional<ValidationCode> findById(Long id);
    Optional<ValidationCode> findByCode(String code);
}
