package com.example.studit.config.fcm;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {
//    private final String API_URL = "https://fcm.googleapis.com/v1/projects/erudite-store-353711/messages:send";
//
//    private final ObjectMapper objectMapper;
//
//    public void sendMessageTo(String targetToken, String title, String body, String path) throws IOException {
//        String message = makeMessage(targetToken, title, body, path);
//
//        OkHttpClient client = new OkHttpClient();
//        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
//        Request request = new Request.Builder()
//                .url(API_URL)
//                .post(requestBody)
//                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
//                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
//                .build();
//    }
//
//    private String makeMessage(String targetToken, String title, String body, String path) throws JsonParseException, JsonProcessingException, com.fasterxml.jackson.core.JsonProcessingException {
//        FcmMessage fcmMessage = FcmMessage.builder()
//                .message(FcmMessage.Message.builder()
//                        .token(targetToken)
//                        .notifications(FcmMessage.Notifications.builder()
//                                .title(title)
//                                .body(body)
//                                .image(null)
//                                .build()
//                        ).build()).validateOnly(false).build();
//        return objectMapper.writeValueAsString(fcmMessage);
//        return "";
//    }
//
//    private String getAccessToken() throws IOException {
//        String firebaseConfigPath = "firebase/firebase_service_key.json";
//
//        GoogleCredentials googleCredentials = GoogleCredentials
//                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
//                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
//
//        googleCredentials.refreshIfExpired();
//        return googleCredentials.getAccessToken().getTokenValue();
//    }
}
