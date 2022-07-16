package com.example.studit.service;

import com.example.studit.domain.User.User;
import com.example.studit.domain.notification.NotificationType;
import com.example.studit.domain.posting.Posting;
import com.example.studit.dto.FCMRequestDto;
import com.example.studit.repository.BulletinBoardRepository;
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
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void sendMessageTo(Long postingId, NotificationType notificationType) throws IOException {
        
        final String API_URL = "https://fcm.googleapis.com/v1/projects/"+ fireBaseId +"/messages:send";
        //String notifyTitle = "[" + notificationType.toString()  + "]" + title;

        Posting posting = postingRepository.findById(postingId)
                .orElseThrow(NoSuchElementException::new);

        User user = posting.getUser();

        String title = user.getNickname()+"님, 게시물에 댓글이 달렸어요.";
        /*String body = posting.getTitle();
        String targetToken = user.getFcmToken();*/

        String message = makeMessage(user.getFcmToken(), title, posting.getTitle());
        logger.error("sendmessageto :"+message);

        //okhttp 객체 생성
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(API_URL)
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

        //logger.error("getAccessToken :"+googleCredentials);

        /*
        서버 키 확인
        String token = googleCredentials.getAccessToken().getTokenValue();
        final Logger logger = LoggerFactory.getLogger(getClass());
        logger.error(token);
        return token;*/

        //google credentials 객체의 토큰값을 가져옴
        return googleCredentials.getAccessToken().getTokenValue();
    }

    //db user 테이블에 fcm 토큰 정보 업데이트
    public User addFcmToken(Long userId, String fcmToken) {
        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        user.setFcmToken(fcmToken);
        userRepository.save(user);

        return user;
    }
}
