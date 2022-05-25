package com.example.studit.domain;

import com.example.studit.domain.posting.Posting;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime localDateTime = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id")
    private Posting posting;

    private String content;

    @Builder
    public Comment(User user, LocalDateTime localDateTime, Posting posting, String content){
        this.user = user;
        this.localDateTime = localDateTime;
        this.posting = posting;
        this.content = content;
    }

    public void addComment(){
        posting.getComments().add(this);
    }
}
