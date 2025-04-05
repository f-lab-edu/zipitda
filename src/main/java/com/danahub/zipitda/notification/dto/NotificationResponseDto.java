package com.danahub.zipitda.notification.dto;

import java.time.LocalDateTime;

public record NotificationResponseDto(
        Long id,
        String message,
        String type,
        boolean readYn,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){ }