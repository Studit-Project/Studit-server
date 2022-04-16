package com.example.studit.service;

import com.example.studit.domain.User;
import com.example.studit.dto.JwtRequestDto;
import com.example.studit.dto.JwtResponseDto;
import com.example.studit.dto.UserJoinDto;
import com.example.studit.repository.UserRepository;
import com.example.studit.security.JwtTokenProvider;
import com.example.studit.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    /*
    회원가입
     */
    public Long join(UserJoinDto userJoinDto){
        userJoinDto.setPassword(passwordEncoder.encode(userJoinDto.getPassword()));
        validateDuplicateMember(userJoinDto);

        User user = userRepository.save(userJoinDto.toEntity());
//        user.encryptPassword(passwordEncoder);
        return user.getId();
    }

    private void validateDuplicateMember(UserJoinDto userJoinDto) {
        List<User> findUsers = userRepository.findUsersByPhone(userJoinDto.getPhone());
        List<User> findEmails = userRepository.findUsersByEmail(userJoinDto.getEmail());
        if(!findUsers.isEmpty())
            throw new IllegalStateException("이미 등록된 번호입니다.");

        if(!findEmails.isEmpty())
            throw new IllegalStateException("이미 등록된 이메일입니다.");
    }

    /*
   로그인
    */

    public JwtResponseDto login(JwtRequestDto request) throws Exception{
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword())
        );
        return createJwtToken(authentication);
    }

    private JwtResponseDto createJwtToken(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(principal);
        return new JwtResponseDto(token);
    }
}
