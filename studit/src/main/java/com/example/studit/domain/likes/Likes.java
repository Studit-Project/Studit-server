package com.example.studit.domain.likes;

import com.example.studit.domain.BaseEntity;
import com.example.studit.domain.User.User;
import com.example.studit.domain.posting.Posting;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Likes extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id")
    private Posting posting;

    public Likes(User user, Posting posting){
        this.user = user;
        this.posting = posting;
    }
}
