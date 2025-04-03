package com.danahub.zipitda.terms.domain;

import com.danahub.zipitda.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
// 약관 엔티티
@Entity
@Table(name = "terms") // 약관 테이블
@Getter
@Setter
public class Terms extends BaseEntity {

    @EmbeddedId
    private TermsId id; // 복합키 (title + version)

    @Column(nullable = false)
    private String content; // 약관 내용

    @Column(nullable = false)
    private Boolean required; // 필수 약관 여부 (true: 필수, false: 선택)

    @Version
    @Column(name = "version_number")
    private Long versionNumber; // 낙관적 락용 필드

}