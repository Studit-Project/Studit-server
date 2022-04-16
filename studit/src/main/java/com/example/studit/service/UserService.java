package com.example.studit.service;

import com.example.studit.domain.User;
import com.example.studit.dto.UserJoinDto;
import com.example.studit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
    회원가입
     */
    public Long join(UserJoinDto userJoinDto){
        userJoinDto.setPassword(passwordEncoder.encode(userJoinDto.getPassword()));
        validateDuplicateMember(userJoinDto);


        User user = userRepository.save(userJoinDto.toEntity());
        return user.getId();
    }

    private void validateDuplicateMember(UserJoinDto userJoinDto) {
        List<User> findUsers = userRepository.findByPhone(userJoinDto.getPhone());
        List<User> findEmails = userRepository.findByEmail(userJoinDto.getEmail());
        if(!findUsers.isEmpty())
            throw new IllegalStateException("이미 등록된 번호입니다.");

        if(!findEmails.isEmpty())
            throw new IllegalStateException("이미 등록된 이메일입니다.");
    }


    /*
    로그인
     */
}
