package com.danahub.zipitda.community.controller;

import com.danahub.zipitda.common.dto.CommonResponse;
import com.danahub.zipitda.common.security.CustomUserDetails;
import com.danahub.zipitda.community.dto.BookmarkRequestDto;
import com.danahub.zipitda.community.dto.BookmarkResponseDto;
import com.danahub.zipitda.community.service.BookmarkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/community/Bookmarks")
@RequiredArgsConstructor
@Tag(name = "Bookmark", description = "북마크 관리 API")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    @PostMapping
    public CommonResponse<BookmarkResponseDto> toggleBookmark(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody BookmarkRequestDto requestDto
    ) {
        Long userId = userDetails.getUserId();
        return CommonResponse.success(bookmarkService.toggleBookmark(userId, requestDto));
    }
}
