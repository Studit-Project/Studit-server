package com.example.studit.domain.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Getter
@NoArgsConstructor
public class RedisChatMessage implements Serializable {
    private Long roomId;
    private String sender;
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
    private String message;

    public void updateSender(String sender) {
        this.sender = sender;
    }

    public void updateAdminMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public void updateUserMessage(String sender) {
        this.sender = sender;
    }

}
