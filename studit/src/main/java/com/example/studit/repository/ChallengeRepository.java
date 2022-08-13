package com.example.studit.repository;

import com.example.studit.domain.challenge.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    @Query(value = "select * from Challenge c where c.status = 'ACTIVE'"
            ,nativeQuery = true)
    List<Challenge> findAllChallenge();
}
