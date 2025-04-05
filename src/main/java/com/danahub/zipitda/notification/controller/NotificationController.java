package com.danahub.zipitda.notification.controller;

import com.danahub.zipitda.common.dto.CommonResponse;
import com.danahub.zipitda.common.exception.ErrorType;
import com.danahub.zipitda.common.exception.ZipitdaException;
import com.danahub.zipitda.common.security.CustomUserDetails;
import com.danahub.zipitda.notification.dto.NotificationListResponseDto;
import com.danahub.zipitda.notification.dto.NotificationResponseDto;
import com.danahub.zipitda.notification.service.NotificationService;
import com.danahub.zipitda.user.domain.User;
import com.danahub.zipitda.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "알림", description = "")
@RequiredArgsConstructor
public class NotificationController {

    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @GetMapping
    public CommonResponse<NotificationListResponseDto> getUserNotifications(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        return CommonResponse.success(notificationService.getNotifications(userId));
    }

    @PatchMapping("/{notificationId}/read")
    public void markAsRead(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId, userDetails.getUserId());
    }
}
