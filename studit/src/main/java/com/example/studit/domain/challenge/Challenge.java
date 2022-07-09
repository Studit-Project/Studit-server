package com.example.studit.domain.challenge;

import com.example.studit.domain.BaseEntity;
import com.example.studit.domain.Image.Image;
import com.example.studit.domain.enumType.StudyStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    //말머리
    @Enumerated(EnumType.STRING)
    private Subject subject;

    private String contents;

    //이미지 리스트
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

}
