package com.example.studit.security;

import com.example.studit.domain.User;
import com.example.studit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByPhone(username)
                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 사용자입니다."));
        return new UserDetailsImpl(user);
    }
}
