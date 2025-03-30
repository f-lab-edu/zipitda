package com.danahub.zipitda.community.controller;

import com.danahub.zipitda.common.dto.CommonResponse;
import com.danahub.zipitda.community.dto.LikeRequestDto;
import com.danahub.zipitda.community.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/community/likes")
@RequiredArgsConstructor
@Tag(name = "Like", description = "좋아요 관리 API")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    @Operation(summary = "좋아요 API", description = "좋아요를 클릭합니다.")
    public void likePost(@RequestBody LikeRequestDto requestDto) {
        likeService.likePost(requestDto.userId(), requestDto.postId());
    }
    @DeleteMapping
    @Operation(summary = "좋아요 해제 API", description = "좋아요를 해제합니다.")
    public void unlikePost(@RequestBody LikeRequestDto requestDto) {
        likeService.unlikePost(requestDto.userId(), requestDto.postId());
    }

    @GetMapping
    @Operation(summary = "좋아요 수 조회 API", description = "해당 포스트의 좋아요 수를 조회합니다.")
    public CommonResponse<Long> countPostLikes(@RequestParam Long postId) {
        return CommonResponse.success(likeService.countPostLikes(postId));
    }
}
