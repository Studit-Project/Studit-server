package com.example.studit.repository;

import com.example.studit.domain.enumType.Category;
import com.example.studit.domain.enumType.Gender;
import com.example.studit.domain.enumType.Target;
import com.example.studit.domain.posting.Posting;
import com.example.studit.domain.posting.Province;
import com.example.studit.domain.study.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository <Posting, Long> {
    List<Posting> findAllByCategory(Category category);

    List<Posting> findByTitleContaining(@Param("keyword") String keyword);

    @Query("select p from Posting p where p.target IN (:targets) and p.gender IN (:genders) and p.province IN (:provinces) and p.activity IN (:activities)")
    List<Posting> findByFilter(@Param("targets") List<Target> targets, @Param("genders") List<Gender> genders, @Param("provinces") List<Province> provinces, @Param("activities") List<Activity> activities);
}
