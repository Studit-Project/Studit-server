package com.example.studit.repository;

import com.example.studit.domain.study.MyStudy;
import com.example.studit.domain.study.ParticipatedStudy;
import com.example.studit.domain.study.Study;
import com.example.studit.dto.StudyManageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {

    Collection<? extends Study> findAllByLeaderIdIn(Set<Long> Leader);


    Collection<? extends Study> findAllByParticipatedMembersIdIn(Set<Long> participatedMembers);

//    Collection<? extends Study> findAllByLeader(Set<MyStudy> myStudySet);
//
//    Collection<? extends Study> findAllByParticipatedMembers(Set<ParticipatedStudy> participatedStudySet);

//    Collection<? extends Study> findByLeader(Set<MyStudy> myStudySet);
//
//    Collection<? extends Study> findByParticipatedMembers(Set<ParticipatedStudy> participatedStudySet);

//    Collection<? extends Study> findAllByMyStudy(List<MyStudy> myStudies);
}
