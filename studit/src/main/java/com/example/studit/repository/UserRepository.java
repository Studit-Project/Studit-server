package com.example.studit.repository;

import com.example.studit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);

    Optional<User> findByEmail(String email);
    
    List<User> findUsersByPhone(String phone);

    List<User> findUsersByEmail(String email);

    User findByUserName(String name);

    User findUserByNickname(String nickname);
}
