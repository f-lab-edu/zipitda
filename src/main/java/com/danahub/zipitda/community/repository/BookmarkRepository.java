package com.danahub.zipitda.community.repository;

import com.danahub.zipitda.community.domain.Bookmark;
import com.danahub.zipitda.community.domain.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Bookmark findByUserIdAndTargetTypeAndTargetId(Long userId, TargetType targetType, Long targetId);

    int countByTargetTypeAndTargetId(TargetType targetType, Long targetId);
}