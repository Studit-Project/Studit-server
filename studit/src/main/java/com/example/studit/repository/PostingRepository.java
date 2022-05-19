package com.example.studit.repository;

import com.example.studit.domain.Category;
import com.example.studit.domain.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository <Posting, Long> {
    List<Posting> findAllByCategory(Category category);
}
