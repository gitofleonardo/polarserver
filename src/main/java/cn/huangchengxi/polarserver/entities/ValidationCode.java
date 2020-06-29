package cn.huangchengxi.polarserver.entities;

import javax.persistence.*;

@SuppressWarnings("all")
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class ValidationCode {
    @Id
    @GeneratedValue
    Long id;
    private String email;
    @Column(name = "code")
    private String code;

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
