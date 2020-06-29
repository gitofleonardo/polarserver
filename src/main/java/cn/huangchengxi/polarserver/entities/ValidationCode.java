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
    private Long time;
    private Long ttl;

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

    public Long getTime() {
        return time;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
