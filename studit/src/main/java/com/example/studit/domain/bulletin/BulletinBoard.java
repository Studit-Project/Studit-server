package com.example.studit.domain.bulletin;

import com.example.studit.domain.BaseEntity;
import com.example.studit.domain.User.User;
import com.example.studit.domain.bulletin.dto.PostReq;
import com.example.studit.domain.comment.Comment;
import com.example.studit.domain.study.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class BulletinBoard extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bulletin_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String content;

    @OneToMany(mappedBy = "bulletin_board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public BulletinBoard(Study study, User user, PostReq postReq){
        this.study = study;
        this.user = user;
        this.title = postReq.getTitle();
        this.content = postReq.getContent();
    }
}
