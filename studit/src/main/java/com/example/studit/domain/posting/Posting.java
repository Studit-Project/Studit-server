package com.example.studit.domain.posting;

import com.example.studit.domain.BaseEntity;
import com.example.studit.domain.comment.Comment;
import com.example.studit.domain.User.User;
import com.example.studit.domain.enumType.Category;
import com.example.studit.domain.enumType.Gender;
import com.example.studit.domain.enumType.StudyStatus;
import com.example.studit.domain.enumType.Target;
import com.example.studit.domain.likes.Likes;
import com.example.studit.domain.posting.dto.PatchPostingReq;
import com.example.studit.domain.posting.dto.PostCreateReq;
import com.example.studit.domain.study.Activity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Posting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posting_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Activity activity;

    @Enumerated(EnumType.STRING)
    private Target target;

    @Enumerated(EnumType.STRING)
    private StudyStatus studyStatus = StudyStatus.RECRUITING;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String title;

    @Enumerated(EnumType.STRING)
    private Province province;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    private LocalDateTime localDateTime = LocalDateTime.now();

    private String content;

    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    //좋아요 한 유저
    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

    public void addLiked(Likes like){
        likes.add(like);
    }

    @Builder
    public Posting(Category category, Province province, String title, User user, String content){
        this.category = category;
        this.province = province;
        this.title = title;
        this.user = user;
        this.content = content;
    }

    public Posting(PostCreateReq postCreateReq, User user){
        this.category = postCreateReq.getCategory();
        this.activity = postCreateReq.getActivity();
        this.province = postCreateReq.getProvince();
        this.title = postCreateReq.getTitle();
        this.user = user;
        this.content = postCreateReq.getContent();
        this.target = postCreateReq.getTarget();
        this.gender = postCreateReq.getGender();
    }

    public void updatePosting(PatchPostingReq patchPostingReq) {
        this.category = patchPostingReq.getCategory();
        this.activity = patchPostingReq.getActivity();
        this.target = patchPostingReq.getTarget();
        this.gender = patchPostingReq.getGender();
        this.studyStatus = patchPostingReq.getStudyStatus();
        this.province = patchPostingReq.getProvince();
        this.title = patchPostingReq.getTitle();
        this.content = patchPostingReq.getContent();
    }
}
