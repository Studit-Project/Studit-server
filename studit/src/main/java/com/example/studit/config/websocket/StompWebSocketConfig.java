package com.example.studit.config.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker //stomp를 사용하기 위해 선언
//@RequiredArgsConstructor
//@EnableWebSocket
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    private final ChatHandler chatHandler;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(chatHandler, "ws/chat")
//                .setAllowedOriginPatterns("*")
//                .withSockJS();
//    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        //WebSockdet 또는 SockJS Client가 웹소켓 핸드셰이크 커넥션을 생성할 경로
        registry.addEndpoint("/stomp/chat")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    /**어플리케이션 내부에서 사용할 path 지정 가능**/
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        //Client에서 SEND 요청 처리
        //test 경로로 시작하는 STOMP 메세지의 "destination" 헤더는 @Controller 객체의 @MessageMapping 메서드로 라우팅된다.
        registry.setApplicationDestinationPrefixes("/pub");
        //내장된 메세지 브로커를 사용해 Client에게 Subscriptions, Broadcasting 기능을 제공한다. 또한 /topic, /queue로 시작하는 "destination" 헤더를 가진 메세지를 브로커로 라우팅한다.
        registry.enableSimpleBroker("/sub");
    }

}
