package com.example.studit.repository;

import com.example.studit.domain.Image.Image;
import com.example.studit.domain.challenge.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByChallenge(Challenge challenge);
}
