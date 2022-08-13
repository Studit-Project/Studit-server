package com.example.studit.repository;


import com.example.studit.domain.User.User;
import com.example.studit.domain.challenge.Challenge;
import com.example.studit.domain.challenge.ChallengeParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeParticipantRepository extends JpaRepository<ChallengeParticipant, Long> {

    Optional<ChallengeParticipant> findByChallengeAndUser(Challenge challenge, User user);

}
