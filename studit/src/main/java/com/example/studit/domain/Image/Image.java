package com.example.studit.domain.Image;

import com.example.studit.domain.BaseEntity;
import com.example.studit.domain.challenge.Challenge;

import javax.persistence.*;

@Entity
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    private String path;
}
