package com.example.studit.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posting_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    private LocalDateTime localDateTime = LocalDateTime.now();

    private String content;

    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
