package com.danahub.zipitda.terms.dto;

import java.util.List;

public record TermsListResponseDto(
        List<TermsResponseDto> termsList
) {
    public static TermsListResponseDto from(List<TermsResponseDto> list) {
        return new TermsListResponseDto(list);
    }
}