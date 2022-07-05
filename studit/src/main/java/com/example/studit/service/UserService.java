package com.example.studit.service;

import com.example.studit.config.exception.BaseException;
import com.example.studit.config.exception.BaseResponseStatus;
import com.example.studit.domain.User.User;
import com.example.studit.domain.User.dto.PatchDetailReq;
import com.example.studit.domain.User.dto.ProfileDto;
import com.example.studit.domain.invitation.Invitation;
import com.example.studit.domain.invitation.dto.GetAllRes;
import com.example.studit.dto.JwtRequestDto;
import com.example.studit.dto.JwtResponseDto;
import com.example.studit.domain.User.dto.UserJoinDto;
import com.example.studit.repository.InvitationRepository;
import com.example.studit.repository.StudyRepository;
import com.example.studit.repository.UserRepository;
import com.example.studit.security.JwtTokenProvider;
import com.example.studit.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final InvitationRepository invitationRepository;
    private final StudyRepository studyRepository;

    /**회원가입**/
    public Long join(UserJoinDto userJoinDto) throws BaseException {
        userJoinDto.setPassword(passwordEncoder.encode(userJoinDto.getPassword()));
        validateDuplicateMember(userJoinDto);

        User user = userRepository.save(userJoinDto.toEntity());
        return user.getId();
    }

    private void validateDuplicateMember(UserJoinDto userJoinDto) throws BaseException {
        List<User> findById = userRepository.findByIdentity(userJoinDto.getIdentity());
        List<User> findUsers = userRepository.findUsersByPhone(userJoinDto.getPhone());
        List<User> findEmails = userRepository.findUsersByEmail(userJoinDto.getEmail());

        if(!findById.isEmpty()) {
            throw new BaseException(BaseResponseStatus.DOUBLE_CHECK_ID);
        }

        if(!findUsers.isEmpty()) {
            throw new BaseException(BaseResponseStatus.DOUBLE_CHECK_ID);
        }

        if(!findEmails.isEmpty()) {
            throw new BaseException(BaseResponseStatus.INVALID_EMAIL);
        }
    }

    /**로그인**/
    public JwtResponseDto login(JwtRequestDto request) {
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

    /**현재 로그인한 유저 정보 반환**/
    public User getUserFromAuth(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName());
//        User user = userRepository.findByEmail(authentication.getName());
        return user;

    }

    /**유저 세부 정보 추가**/
    public void addDetailInfo(PatchDetailReq patchDetailReq) throws BaseException {
//        User user = getUserFromAuth();
        Optional<User> user = userRepository.findByPhone(patchDetailReq.getNumber());
        Optional<User> isPresent = userRepository.findByNickname(patchDetailReq.getNickname());

        if(!isPresent.isEmpty()){
            throw new BaseException(BaseResponseStatus.INVALID_NICKNAME);
        }

        user.get().addDetailInfo(patchDetailReq);
    }

    /**프로필**/
    public ProfileDto getProfile() {
        User user = getUserFromAuth();
        ProfileDto profileDto = new ProfileDto(user);
        return profileDto;
    }

    /**스터디 초대 목록**/
    public List<GetAllRes> getInvitations() {
        User user = getUserFromAuth();
        List<Invitation> invitations = invitationRepository.findByUser(user);
        List<GetAllRes> getAllRes = invitations.stream()
                .map(GetAllRes::new)
                .collect(Collectors.toList());

        return getAllRes;
    }

}
