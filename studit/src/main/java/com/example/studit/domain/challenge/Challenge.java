package com.example.studit.domain.challenge;

import com.example.studit.domain.BaseEntity;
import com.example.studit.domain.Image.Image;
import com.example.studit.domain.Status;
import com.example.studit.domain.User.User;
import com.example.studit.domain.enumType.StudyStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Challenge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //말머리
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject subject;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyStatus challengeStatus;

    //이미지 리스트
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    public Challenge(Subject subject, String content, String title, User user) {
        this.subject = subject;
        this.content = content;
        this.title = title;
        this.user = user;
        this.challengeStatus = StudyStatus.RECRUITING;
    }

    public void saveImg(Image image){
        this.images.add(image);

        if(image.getChallenge() != this){
            image.setChallenge(this);
        }
    }

    public void deleteChallenge(){
        changeStatus(Status.DELETED);
    }

    public void modifyChallenge(Subject subject, String content, String title){
        this.subject = subject;
        this.content = content;
        this.title = title;
    }

    public void changeChallengeStatus(StudyStatus status){
        this.challengeStatus = status;
    }

}
