package com.danahub.zipitda.community.service;

import com.danahub.zipitda.common.exception.ErrorType;
import com.danahub.zipitda.common.exception.ZipitdaException;
import com.danahub.zipitda.community.domain.Like;
import com.danahub.zipitda.community.repository.LikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    private static final String TARGET_TYPE_POST = "POST";

    @Transactional
    public void likePost(Long userId, Long postId) {
        boolean alreadyLiked = likeRepository
                .findByUserIdAndTargetTypeAndTargetId(userId, TARGET_TYPE_POST, postId)
                .isPresent();

        if (alreadyLiked) {
            throw new ZipitdaException(ErrorType.LOCK_FAILED);
        }

        Like like = Like.builder()
                .userId(userId)
                .targetType(TARGET_TYPE_POST)
                .targetId(postId)
                .build();

        likeRepository.save(like);
    }

    public void unlikePost(Long userId, Long postId) {
        Like like = likeRepository
                .findByUserIdAndTargetTypeAndTargetId(userId, TARGET_TYPE_POST, postId)
                .orElseThrow(() -> new ZipitdaException(ErrorType.RESOURCE_NOT_FOUND));

        likeRepository.delete(like);
    }

    public long countPostLikes(Long postId) {
        return likeRepository.countByTargetTypeAndTargetId(TARGET_TYPE_POST, postId);
    }
}