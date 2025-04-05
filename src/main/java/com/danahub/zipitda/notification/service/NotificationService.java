package com.danahub.zipitda.notification.service;

import com.danahub.zipitda.notification.dto.NotificationListResponseDto;
import com.danahub.zipitda.notification.dto.NotificationResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    // 유저 전체 알림 조회
    public NotificationListResponseDto getNotifications(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        List<NotificationResponseDto> dtoList = notifications.stream()
                .map(NotificationResponseDto::from)
                .toList();

        return new NotificationListResponseDto(dtoList);
    }

    // 단일 알림 읽음 처리
    public void markAsRead(Long notificationId, Long userId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found"));

        if (!notification.getUserId().equals(userId)) {
            throw new AccessDeniedException("본인의 알림만 읽음 처리할 수 있습니다.");
        }

        notification.markAsRead();
    }
}