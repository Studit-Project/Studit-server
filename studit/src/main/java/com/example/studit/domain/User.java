package com.example.studit.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {
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

    @Builder
    public User(String userName, String phone, String pwd, String email){
        this.userName = userName;
        this.phone = phone;
        this.pwd = pwd;
        this.email = email;
    }
}
