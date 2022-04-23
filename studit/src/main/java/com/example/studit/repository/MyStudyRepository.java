package com.example.studit.repository;

import com.example.studit.domain.study.MyStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyStudyRepository extends JpaRepository<MyStudy, Long> {
}
