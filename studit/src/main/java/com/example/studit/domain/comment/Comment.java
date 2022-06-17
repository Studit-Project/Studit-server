package com.example.studit.domain.comment;

import com.example.studit.domain.BaseEntity;
import com.example.studit.domain.User.User;
import com.example.studit.domain.bulletin.BulletinBoard;
import com.example.studit.domain.posting.Posting;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    private LocalDateTime localDateTime = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id")
    private Posting posting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bulletin_id")
    private BulletinBoard bulletinBoard;

    private String content;

    @Builder
    public Comment(User user, LocalDateTime localDateTime, Posting posting, String content){
        this.user = user;
        this.localDateTime = localDateTime;
        this.posting = posting;
        this.content = content;
    }

    public Comment(User user, BulletinBoard bulletinBoard, String content){
        this.user = user;
        this.bulletinBoard = bulletinBoard;
        this.content = content;
    }

    public void addComment(){
        posting.getComments().add(this);
    }

    public void addToBulletinBoard(){
        bulletinBoard.getComments().add(this);
    }
}
