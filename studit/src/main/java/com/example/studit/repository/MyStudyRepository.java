package com.example.studit.repository;

import com.example.studit.domain.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyStudyRepository extends JpaRepository<Study, Long> {
}
