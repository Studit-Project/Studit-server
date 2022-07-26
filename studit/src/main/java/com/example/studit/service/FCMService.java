package com.example.studit.service;

import com.example.studit.domain.User.User;
import com.example.studit.domain.challenge.Challenge;
import com.example.studit.domain.notification.NotificationType;
import com.example.studit.domain.posting.Posting;
import com.example.studit.dto.FCMRequestDto;
import com.example.studit.repository.ChallengeRepository;
import com.example.studit.repository.PostingRepository;
import com.example.studit.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMService {

    @Value("${firebase.project-id}")
    String fireBaseId;

    @Value("${firebase.key-path}")
    String fcmKeyPath;

    private final ObjectMapper objectMapper; //jackson을 사용해 dto 객체를 String으로 변환
    private final UserRepository userRepository;
    private final PostingRepository postingRepository;

    private final ChallengeRepository challengeRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void sendMessageTo(Long contentId, NotificationType notificationType) throws IOException {

        String info;
        String contentTitle = null;
        User user = new User();

        switch (notificationType){
            case COMMENT: case LIKES:
                Posting posting = postingRepository.findById(contentId)
                        .orElseThrow(NoSuchElementException::new);
                user = posting.getUser();
                contentTitle = posting.getTitle();
                break;
            case ACCEPT: case REFUSE: case EXPEL: case INVITE:
                Challenge challenge = challengeRepository.findById(contentId)
                        .orElseThrow(NoSuchElementException::new);
                user = challenge.getUser();
                contentTitle = challenge.getContents().substring(0,15) + "...";
                break;
        }

        info = user.getNickname() + "님, " + notificationType.getMessage();

        String message = makeMessage(user.getFcmToken(), info, contentTitle);
        setBuild(message);
    }

    public void setBuild(String message) throws IOException {
        //okhttp 객체 생성
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/v1/projects/"+ fireBaseId +"/messages:send")
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer "+getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .build();

        Response response = okHttpClient.newCall(request)
                .execute();

        logger.error(response.toString());
    }

    public String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        //빌더 패턴을 이용해 fcm Request 객체 생성
        FCMRequestDto fcmRequestDto = FCMRequestDto.builder()
                .message(FCMRequestDto.Message.builder()
                .token(targetToken)
                .notification(FCMRequestDto.Notification.builder()
                        .title(title)
                        .body(body)
                        .image(null)
                        .build()
                ).build()
                )
                .validate_only(false)
                .build();

        //생성한 dto를 string으로 반환
        return objectMapper.writeValueAsString(fcmRequestDto);
    }


    //FCM 서버에 push 요청시 필요한 서버측 토큰 발급
    public String getAccessToken() throws IOException {

        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(fcmKeyPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();

        /*
        서버 키 확인
        String token = googleCredentials.getAccessToken().getTokenValue();
        logger.error(token);
        return token;*/

        //google credentials 객체의 토큰값을 가져옴
        return googleCredentials.getAccessToken().getTokenValue();
    }

    //db user 테이블에 fcm 토큰 정보 업데이트
    public User addFcmToken(Long userId, String fcmToken) {
        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        user.addFcmToken(fcmToken);
        userRepository.save(user);

        return user;
    }
}
