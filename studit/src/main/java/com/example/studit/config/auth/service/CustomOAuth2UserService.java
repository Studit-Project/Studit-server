package com.example.studit.config.auth.service;

import com.example.studit.config.WebSecurityConfig;
import com.example.studit.config.auth.dto.OAuthAttributes;
import com.example.studit.config.auth.dto.SessionUser;
import com.example.studit.domain.User.User;
import com.example.studit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail()).orElse(attributes.toEntity());

        if(user.getId() == null) {
            user.createSocialId(RandomStringUtils.randomAlphanumeric(30).toUpperCase());
        }

//        /** 순환 참조 없이 비밀번호 암호화 하기 **/
//        if(user.getId() == null) {
//            user.createSocialPw(passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(50)));
//            user.createSocialPw(webSecurityConfig.getPasswordEncoder().encode(RandomStringUtils.randomAlphanumeric(50)));
//        }

        return userRepository.save(user);

    }
}
