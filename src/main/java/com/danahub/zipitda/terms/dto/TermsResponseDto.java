package com.danahub.zipitda.terms.dto;

import com.danahub.zipitda.terms.domain.Terms;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

public record TermsResponseDto(
        String title,
        Integer version,
        String content,
        Boolean required,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
        public static TermsResponseDto fromEntity(Terms terms) {
                return new TermsResponseDto(
                        terms.getId().getTitle(),           // 복합키에서 title 추출
                        terms.getId().getVersion(),         // 복합키에서 version 추출
                        terms.getContent(),
                        terms.getRequired(),
                        terms.getCreatedAt(),
                        terms.getUpdatedAt()
                );
        }
}