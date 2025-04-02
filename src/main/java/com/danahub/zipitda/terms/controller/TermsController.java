package com.danahub.zipitda.terms.controller;

import com.danahub.zipitda.common.dto.CommonResponse;
import com.danahub.zipitda.terms.domain.Terms;
import com.danahub.zipitda.terms.dto.TermsResponseDto;
import com.danahub.zipitda.terms.service.TermsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/terms")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "term", description = "약관 API")
public class TermsController {

    private final TermsService termsService;

    @GetMapping
    @Operation(summary = "약관 전체 조회 API", description = "약관을 전체 조회합니다.")
    public CommonResponse<Page<TermsResponseDto>> getAllTerms(Pageable pageable) {
        return CommonResponse.success(termsService.getAllTerms(pageable));
    }

    @GetMapping("/{title}/{version}")
    @Operation(summary = "특정 약관 조회 API", description = "약관제목과 버전으로 특정 약관을 가져옵니다.")
    public CommonResponse<TermsResponseDto> getTermsByTitleAndVersion(
            @PathVariable String title,
            @PathVariable Integer version) {
        return CommonResponse.success(termsService.getTermsByTitleAndVersion(title, version));
    }

}