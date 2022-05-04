package com.example.studit.domain.challenge;

import com.example.studit.domain.enumType.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id")
    private Long id;

    //챌린지 목표
    @Column(nullable = false)
    private String goal;

    //시작 날짜
    private LocalDateTime startDate;

    //끝나는 날짜
    private LocalDateTime endDate;

    //모집 상태
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "challenge",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MyChallenge myChallenge;

    //참여 횟수
    private long numberOfTimes;
}
