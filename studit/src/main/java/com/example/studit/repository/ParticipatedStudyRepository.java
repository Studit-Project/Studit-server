package com.example.studit.repository;

import com.example.studit.domain.User;
import com.example.studit.domain.study.ParticipatedStudy;
import com.example.studit.dto.StudyManageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ParticipatedStudyRepository extends JpaRepository<ParticipatedStudy, Long> {
}
