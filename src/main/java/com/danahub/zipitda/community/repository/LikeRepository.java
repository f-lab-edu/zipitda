package com.danahub.zipitda.community.repository;

import com.danahub.zipitda.community.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
