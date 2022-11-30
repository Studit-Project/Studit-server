package com.example.studit.service.chat;

import com.example.studit.config.exception.BaseException;
import com.example.studit.config.exception.BaseResponseStatus;
import com.example.studit.domain.User.User;
import com.example.studit.domain.chat.ChatRoom;
import com.example.studit.domain.chat.MessageType;
import com.example.studit.domain.chat.RedisChatMessage;
import com.example.studit.repository.ChatMessageRepository;
import com.example.studit.repository.ChatRoomRepository;
import com.example.studit.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber {
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    /**
     * Redis에서 메세지가 발행(pub)되면 대기하고 있던 Redis Subscriber가 해당 메세지 받아 처리함
     */
    public void sendMessage(String publishMessage) throws BaseException {
        try {
            // RedisChatMessage 객체로 맵핑
            RedisChatMessage redisChatMessage = objectMapper
                    .readValue(publishMessage, RedisChatMessage.class);

            // 메세지로부터 채팅방 찾기
            ChatRoom chatRoom = chatRoomRepository.findByStudyId(redisChatMessage.getRoomId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.CHAT_ROOM_NOT_FOUND));

            // 메세지로부터 회원 찾기
            User user = userRepository.findById(Long.parseLong(redisChatMessage.getSender()))
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.USERS_EMPTY_USER_ID));

            // MessageType에 따라 처리
            if (MessageType.ENTER.equals(redisChatMessage.getMessageType())) {
                redisChatMessage.updateAdminMessage("띵-동", user.getNickname() + "님이 입장하셨습니다.");
                user = userRepository.getById(1L);
            }

        } catch (Exception e) {
            log.error("Exception {}", e);
        }
    }
}
