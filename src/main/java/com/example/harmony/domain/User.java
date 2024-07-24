package com.example.harmony.domain;

import com.example.harmony.domain.ShareChat.ShareChat;
import com.example.harmony.type.EProvider;
import com.example.harmony.type.ERole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "serial_id", unique = true)
    private String serialId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private EProvider eProvider;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ERole role;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "is_login", columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean isLogin;

    /* User Info */
    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private Integer age;

    @Column(name = "device_token")
    private String deviceToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ShareChat> diaryList = new ArrayList<>();

    public User(String socialId, String password, EProvider eProvider, ERole role) {
        this.password = password;
        this.eProvider = eProvider;
        this.role = role;
        this.createDate = LocalDate.now();
        this.isLogin = true;
    }

    @Builder
    public User(String serialId, String password, EProvider eProvider, ERole role
            , String name, String phoneNumber, Integer age) {
        this.serialId = serialId;
        this.password = password;
        this.eProvider = eProvider;
        this.role = role;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.createDate = LocalDate.now();
        this.isLogin = false;
        this.age = age;
    }

    public void update(String name, String password, String phoneNumber, Integer age) {
        if (name != null && !name.equals(this.name)) {
            this.name = name;
        }
        if (password != null && !password.equals(this.password)) {
            this.password = password;
        }
        if (phoneNumber != null && !phoneNumber.equals(this.phoneNumber)) {
            this.phoneNumber = phoneNumber;
        }
        if (age != null && !age.equals(this.age)) {
            this.age = age;
        }
    }
    public void update(String password){
        if (password != null && !password.equals(this.password)) {
            this.password = password;
        }
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
