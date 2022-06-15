package com.example.studit.repository;

import com.example.studit.domain.User.User;
import com.example.studit.domain.invitation.Invitation;
import com.example.studit.domain.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findByUser(User user);

    Optional<Invitation> findByStudyAndUser(Study study, User user);
}
