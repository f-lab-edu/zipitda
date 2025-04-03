package com.danahub.zipitda.terms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TermsRequestDto(
        @NotBlank(message = "약관 제목은 필수입니다.")
        String title,
        @NotNull(message = "약관 버전은 필수입니다.")
        Integer version,
        @NotBlank(message = "약관 내용은 필수입니다.")
        String content,
        @NotNull(message = "필수 여부는 필수입니다.")
        Boolean required,
        @NotNull(message = "버전번호는 필수입니다.")
        Long versionNumber
) {}