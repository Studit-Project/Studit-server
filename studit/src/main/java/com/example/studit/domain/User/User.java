package com.example.studit.domain.User;

import com.example.studit.domain.BaseEntity;
import com.example.studit.domain.Comment;
import com.example.studit.domain.MyBadge;
import com.example.studit.domain.User.dto.PatchDetailReq;
import com.example.studit.domain.challenge.MyChallenge;
import com.example.studit.domain.enumType.Gender;
import com.example.studit.domain.enumType.Role;
import com.example.studit.domain.posting.Posting;
import com.example.studit.domain.study.MyStudy;
import com.example.studit.domain.study.ParticipatedStudy;
import com.example.studit.dto.UserInfoDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {
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

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private Role role;

    //목표
    private String goal;

    //수집한 뱃지
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MyBadge> badges = new ArrayList<>();

    //내가 스터디 방장
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MyStudy> myStudies = new ArrayList<>();

    //나는 스터디원
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) //탈퇴하면 스터디, 챌린지에서 나가지도록
    private List<ParticipatedStudy> participatedStudies = new ArrayList<>();

    //내가 만든 챌린지
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MyChallenge> myChallenges = new ArrayList<>();

    //내가 쓴 글
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Posting> postings = new ArrayList<>();

    //내가 단 댓글
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public UserInfoDto toUserInfoDto(){
        UserInfoDto userInfoDto = new UserInfoDto();
        return UserInfoDto.builder().
                user(this).
                build();
    }

    @Builder
    public User(String userName, String phone, String pwd, String email, String nickname){
        this.userName = userName;
        this.phone = phone;
        this.pwd = pwd;
        this.email = email;
        this.role = Role.USER;
        this.nickname = nickname;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        pwd = passwordEncoder.encode(pwd);
    }

    public void addDetailInfo(PatchDetailReq patchDetailReq) {
        this.nickname = patchDetailReq.getNickname();
        this.gender = patchDetailReq.getGender();
        this.birth = patchDetailReq.getBirth();
    }
}