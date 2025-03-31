package com.danahub.zipitda.community.dto;

import com.danahub.zipitda.community.domain.TargetType;

public record BookmarkResponseDto(
        TargetType targetType,
        Long targetId,
        int bookmarkCount,
        boolean bookmarked
){ }