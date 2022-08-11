package com.example.studit.domain.Image;

import com.example.studit.domain.BaseEntity;
import com.example.studit.domain.challenge.Challenge;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    private String url;

    public Image(String url) {
        this.url = url;
    }

    public void setChallenge(Challenge challenge){
        if(this.challenge != null){
            this.challenge.getImages().remove(this);
        }
        this.challenge = challenge;
        if(!challenge.getImages().contains(this)){
            challenge.saveImg(this);
        }
    }

}
