package com.example.studit.domain.study;

import com.example.studit.domain.User;
import com.example.studit.domain.study.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ParticipatedStudy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participated_study_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;
}