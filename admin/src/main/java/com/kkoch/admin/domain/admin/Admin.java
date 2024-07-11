package com.kkoch.admin.domain.admin;

import com.kkoch.admin.domain.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Integer id;

    @Column(unique = true, nullable = false, updatable = false, length = 100)
    private String email;

    @Column(nullable = false, columnDefinition = "char(60)", length = 60)
    private String pwd;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(unique = true, nullable = false, columnDefinition = "char(13)", length = 13)
    private String tel;

    @Column(nullable = false, length = 2)
    private AdminAuth auth;

    @Builder
    private Admin(boolean isDeleted, String email, String pwd, String name, String tel, AdminAuth auth) {
        super(isDeleted);
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.tel = tel;
        this.auth = auth;
    }

    public static Admin of(boolean isDeleted, String email, String pwd, String name, String tel, AdminAuth auth) {
        return new Admin(isDeleted, email, pwd, name, tel, auth);
    }

    public static Admin create(String email, String pwd, String name, String tel, AdminAuth auth) {
        return of(false, email, pwd, name, tel, auth);
    }

    //== 비즈니스 로직 ==//
    public void modifyPwd(String pwd) {
        this.pwd = pwd;
    }

    public void remove() {
        super.remove();
    }
}
