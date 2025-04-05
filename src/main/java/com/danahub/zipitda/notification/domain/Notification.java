package com.danahub.zipitda.notification.domain;


import com.danahub.zipitda.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // 수신자 ID

    private String message; // 알림 내용

    @Enumerated(EnumType.STRING)
    private NotificationType type; // 알림 종류: DELIVERY, ORDER, COMMENT 등

    private boolean readYn; // 읽음 여부

    public void markAsRead() {
        this.readYn = true;
    }
}