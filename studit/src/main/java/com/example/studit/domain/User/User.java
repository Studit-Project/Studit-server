package com.example.studit.domain.User;

import com.example.studit.domain.BaseEntity;
import com.example.studit.domain.Image.Image;
import com.example.studit.domain.challenge.Challenge;
import com.example.studit.domain.comment.Comment;
import com.example.studit.domain.badge.MyBadge;
import com.example.studit.domain.User.dto.PatchDetailReq;
import com.example.studit.domain.enumType.Gender;
import com.example.studit.domain.enumType.Level;
import com.example.studit.domain.enumType.Role;
import com.example.studit.domain.invitation.Invitation;
import com.example.studit.domain.likes.Likes;
import com.example.studit.domain.posting.Posting;
import com.example.studit.domain.study.ParticipatedStudy;
import com.example.studit.domain.User.dto.UserInfoDto;
import com.example.studit.domain.study.Study;
import lombok.*;
import org.hibernate.annotations.Formula;
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

    //id
    @Column(nullable = false, unique = true)
    private String identity;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = false)
    private String phone;

    @Column(nullable = false)
    private String pwd;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(nullable = true, unique = true)
    private String fcmToken;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birth;

    //연령대
    private String ageRange;

    @Enumerated(EnumType.STRING)
    private Role role;

    //상태 메세지
    private String statusMessage;

    //목표
    private String goal;

    @OneToMany(mappedBy = "leader", cascade = CascadeType.ALL)
    private List<Study> studies = new ArrayList<>();

    //수집한 뱃지
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MyBadge> badges = new ArrayList<>();

    //나는 스터디원
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) //탈퇴하면 스터디, 챌린지에서 나가지도록
    private List<ParticipatedStudy> participatedStudies = new ArrayList<>();


    //내가 한 챌린지
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Challenge> challenges = new ArrayList<>();


    //내가 쓴 글
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Posting> postings = new ArrayList<>();

    //내가 단 댓글
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    //내가 좋아요 한 글
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

    //초대된 스터디
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Invitation> invitations = new ArrayList<>();

    public void addLikes(Likes like){
        likes.add(like);
    }

    public void addStudy(Study study){
        studies.add(study);
    }

    public void addInvitation(Invitation invitation){
        invitations.add(invitation);
    }

    public UserInfoDto toUserInfoDto(){
        UserInfoDto userInfoDto = new UserInfoDto();
        return UserInfoDto.builder().
                user(this).
                build();
    }

    @Builder
    public User(String identity, String userName, String phone, String pwd, String email, String nickname, String ageRange){
        this.identity = identity;
        this.userName = userName;
        this.phone = phone;
        this.pwd = pwd;
        this.email = email;
        this.ageRange = ageRange;
        this.role = Role.USER;
        this.nickname = nickname;
        this.level = Level.LV1;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        pwd = passwordEncoder.encode(pwd);
    }

    public void addDetailInfo(PatchDetailReq patchDetailReq) {
        this.nickname = patchDetailReq.getNickname();
        this.gender = patchDetailReq.getGender();
        this.birth = patchDetailReq.getBirth();
//        this.statusMessage = patchDetailReq.getStatusMessage();
    }


    public void createSocialId(String id){
        this.identity = id;
    }

    public void createSocialPw(String pw) {
        this.pwd = pw;
    }

    public void addFcmToken(String fcmToken){
        this.fcmToken = fcmToken;
    }

    public Level levelUp(Long count){

        if(!this.getLevel().equals(Level.LV3)){
            Level nextLevel = Level.getNextLevel(Math.toIntExact(count));
            if(nextLevel != null){
                this.level = nextLevel;
                return nextLevel;
            }
        }
        return null;
    }

    public void addChallenge(Challenge challenge){
        if(!this.challenges.contains(challenge)){
            this.challenges.add(challenge);
        }
    }

    public void updateStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void updatePassword(String password){
        this.pwd = password;
    }
}
