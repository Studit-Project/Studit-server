package com.example.studit.domain.notification;

import com.example.studit.domain.BaseEntity;
import com.example.studit.domain.User.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, of = "id")
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    private String content;

    private String relatedUrl;

    @Column(nullable = false)
    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User receiver;

    @Builder
    public Notification(User receiver, NotificationType notificationType, String content, String url, Boolean isRead) {
        this.receiver = receiver;
        this.notificationType = notificationType;
        this.content = content;
        this.relatedUrl = url;
        this.isRead = isRead;
    }
}
