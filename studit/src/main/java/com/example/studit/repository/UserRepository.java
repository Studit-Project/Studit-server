package com.example.studit.repository;

import com.example.studit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByPhone(String phone);

    List<User> findByEmail(String email);
}
