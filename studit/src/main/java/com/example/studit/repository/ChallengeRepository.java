package com.example.studit.repository;

import com.example.studit.domain.User.User;
import com.example.studit.domain.challenge.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    @Query(value = "select * from Challenge c where c.status = 'ACTIVE'"
            ,nativeQuery = true)
    List<Challenge> findAllChallenge();

    @Query(value = "select * from Challenge c where c.subject in (:subject) and c.status = 'ACTIVE'"
            ,nativeQuery = true)
    List<Challenge> searchChallengeBySubject(List<String> subject);

    @Query(value = "select * from Challenge c " +
            "where (c.title LIKE  %?1% or c.content LIKE  %?1%) and c.status = 'ACTIVE'"
            ,nativeQuery = true)
    List<Challenge> searchChallenge(String keyword);

    Long countByUser(User user);

}
