package com.danahub.zipitda.community.dto;

import com.danahub.zipitda.community.domain.TargetType;

public record BookmarkRequestDto(
        TargetType targetType,
        Long targetId
){ }
