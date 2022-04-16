package com.example.studit.domain;

import lombok.*;
import net.bytebuddy.build.Plugin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String pwd;

    @Column(unique = true)
    private String nickname;

    private Gender gender;

    private String birth;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String userName, String phone, String pwd, String email){
        this.userName = userName;
        this.phone = phone;
        this.pwd = pwd;
        this.email = email;
        this.role = Role.USER;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        pwd = passwordEncoder.encode(pwd);
    }
}
