package com.danahub.zipitda.notification.dto;

import java.util.List;

public record NotificationListResponseDto(
        List<NotificationResponseDto> notifications
) { }
