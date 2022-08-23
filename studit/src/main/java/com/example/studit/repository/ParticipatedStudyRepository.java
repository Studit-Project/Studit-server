package com.example.studit.repository;

import com.example.studit.domain.User.User;
import com.example.studit.domain.study.ParticipatedStudy;
import com.example.studit.domain.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipatedStudyRepository extends JpaRepository<ParticipatedStudy, Long> {
    ParticipatedStudy findByUserAndStudy(User user, Study study);

    List<ParticipatedStudy> findByStudy(Study study);
}
