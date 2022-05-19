package com.example.studit.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long id;

    private String name;

    private String url;

    @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL)
    private List<MyBadge> myBadges = new ArrayList<>();
}
