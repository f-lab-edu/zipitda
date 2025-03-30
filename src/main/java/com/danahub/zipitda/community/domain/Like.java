package com.danahub.zipitda.community.domain;

import com.danahub.zipitda.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes",
       uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "target_type", "target_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 좋아요 ID

    @Column(nullable = false)
    private Long userId; // 좋아요 누른 사용자 ID

    @Column(nullable = false)
    private String targetType; // 좋아요 대상 타입 ("POST", "PRODUCT", "REVIEW")

    @Column(nullable = false)
    private Long targetId; // 좋아요가 속한 대상 ID
}