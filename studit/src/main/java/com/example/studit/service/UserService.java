package com.example.studit.service;

import com.example.studit.config.exception.BaseException;
import com.example.studit.config.exception.BaseResponseStatus;
import com.example.studit.domain.User.User;
import com.example.studit.domain.User.dto.PatchDetailReq;
import com.example.studit.domain.User.dto.ProfileDto;
import com.example.studit.domain.User.dto.StatusMessage;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    String studitEmail;

    /** 회원가입 **/
    public Long join(UserJoinDto userJoinDto) throws BaseException {
        userJoinDto.setPassword(passwordEncoder.encode(userJoinDto.getPassword()));
        validateDuplicateMember(userJoinDto);

        User user = userRepository.save(userJoinDto.toEntity());
        return user.getId();
    }

    private void validateDuplicateMember(UserJoinDto userJoinDto) throws BaseException {
        Optional<User> findById = userRepository.findByIdentity(userJoinDto.getIdentity());

        List<User> findEmails = userRepository.findUsersByEmail(userJoinDto.getEmail());

        if(!findById.isEmpty()) {
            throw new BaseException(BaseResponseStatus.DOUBLE_CHECK_ID);
        }

        if(!findEmails.isEmpty()) {
            throw new BaseException(BaseResponseStatus.INVALID_EMAIL);
        }
    }

    /** 로그인 **/
    public JwtResponseDto login(JwtRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getIdentity(), request.getPassword())
        );
        return createJwtToken(authentication);
    }

    private JwtResponseDto createJwtToken(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(principal);
        return new JwtResponseDto(token);
    }

    /** 현재 로그인한 유저 정보 반환 **/
    public User getUserFromAuth(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        Optional<User> user = userRepository.findByIdentity(name);
        return user.get();

    }

    /** 유저 세부 정보 추가 **/
    public void addDetailInfo(Long userId, PatchDetailReq patchDetailReq) throws BaseException {
//        User user = getUserFromAuth();
        Optional<User> user = userRepository.findById(userId);
        Optional<User> isPresent = userRepository.findByNickname(patchDetailReq.getNickname());

        if(!isPresent.isEmpty()){
            if(isPresent.get() == user.get()){
                user.get().addDetailInfo(patchDetailReq);
            }
            throw new BaseException(BaseResponseStatus.INVALID_NICKNAME);
        }

        user.get().addDetailInfo(patchDetailReq);
    }

    /** 프로필 **/
    public ProfileDto getProfile() {
        User user = getUserFromAuth();
        ProfileDto profileDto = new ProfileDto(user);
        return profileDto;
    }

    /** 스터디 초대 목록 **/
    public List<GetAllRes> getInvitations() {
        User user = getUserFromAuth();
        List<Invitation> invitations = invitationRepository.findByUser(user);
        List<GetAllRes> getAllRes = invitations.stream()
                .map(GetAllRes::new)
                .collect(Collectors.toList());

        return getAllRes;
    }

    /** 상태 메세지만 변경 **/
    public String editStatusMessage(StatusMessage statusMessage) {
        User user = getUserFromAuth();
        user.updateStatusMessage(statusMessage.getStatusMessage());
        return "상태 메세지가 '" + statusMessage.getStatusMessage() + "'로 변경되었습니다.";
    }

    public User modifyPassword(String password) {
        User user = getUserFromAuth();
        user.updatePassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public String findPassword(String email) throws BaseException {
        //해당 이메일을 가진 유저가 있는지 체크
        Optional<User> found = userRepository.findByEmail(email);
        if(found.isEmpty()){
            //없으면 에러
            throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_EMAIL);
        }
        String tempPassword = getTempPassword();
        found.get().updatePassword(passwordEncoder.encode(tempPassword));

        sendMail(tempPassword, found.get().getEmail());

        return userRepository.save(found.get()).getEmail();
    }

    private String getTempPassword() {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };
        int idx;
        String password = "";

        for(int i=0; i<10; i++){
            //마지막 인덱스 : charSet.length-1
            //0 ~ 마지막 인덱스 - 정수 랜덤 생성
            idx = (int) Math.floor(Math.random()*charSet.length);
            password += charSet[idx];
        }

        return password;
    }

    private void sendMail(String password, String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(studitEmail);
        message.setTo(email);
        message.setSubject("[Studit] 임시 비밀번호가 발급되었습니다.");
        message.setText("회원님의 임시 비밀번호는\n\n" +password+
                "\n\n입니다.\n로그인 후 임시 비밀번호를 변경해주세요.");

        mailSender.send(message);
    }
}
