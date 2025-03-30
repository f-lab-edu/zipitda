package com.danahub.zipitda.community.repository;

import com.danahub.zipitda.community.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndTargetTypeAndTargetId(Long userId, String targetType, Long targetId);

    long countByTargetTypeAndTargetId(String targetType, Long targetId);
}