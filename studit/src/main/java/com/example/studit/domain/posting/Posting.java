package com.example.studit.domain.posting;

import com.example.studit.domain.BaseEntity;
import com.example.studit.domain.Comment;
import com.example.studit.domain.User.User;
import com.example.studit.domain.enumType.Category;
import com.example.studit.domain.posting.dto.PostCreateReq;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

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
        this.province = postCreateReq.getProvince();
        this.title = postCreateReq.getTitle();
        this.user = user;
        this.content = postCreateReq.getContent();
    }
}
