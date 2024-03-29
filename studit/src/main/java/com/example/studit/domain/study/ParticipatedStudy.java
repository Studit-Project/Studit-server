package com.example.studit.domain.study;

import com.example.studit.domain.BaseEntity;
import com.example.studit.domain.Status;
import com.example.studit.domain.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Where(clause = "status='ACTIVE'")
@NoArgsConstructor
public class ParticipatedStudy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participated_study_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    public void addUser(User user){
        this.user = user;
    }

    public void addStudy(Study study){
        this.study = study;
    }

    public ParticipatedStudy(User user, Study study) {
        this.user = user;
        this.study = study;
    }

    public void delete() {
        changeStatus(Status.DELETED);
    }
}
