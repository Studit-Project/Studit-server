package com.example.studit.repository;

import com.example.studit.domain.enumType.Category;
import com.example.studit.domain.posting.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository <Posting, Long> {
    List<Posting> findAllByCategory(Category category);

    List<Posting> findByTitleContaining(String keyword);
}
