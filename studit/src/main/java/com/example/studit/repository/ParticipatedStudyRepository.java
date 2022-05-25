package com.example.studit.repository;

import com.example.studit.domain.study.ParticipatedStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipatedStudyRepository extends JpaRepository<ParticipatedStudy, Long> {
}
