package com.danahub.zipitda.terms.controller;

import com.danahub.zipitda.common.dto.CommonResponse;
import com.danahub.zipitda.terms.domain.Terms;
import com.danahub.zipitda.terms.dto.TermsListResponseDto;
import com.danahub.zipitda.terms.dto.TermsRequestDto;
import com.danahub.zipitda.terms.dto.TermsResponseDto;
import com.danahub.zipitda.terms.service.TermsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terms")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "term", description = "약관 API")
public class TermsController {

    private final TermsService termsService;

    @GetMapping
    @Operation(summary = "전체 약관 페이징 조회", description = "모든 약관(version 포함)을 페이징하여 조회합니다.")
    public CommonResponse<Page<TermsResponseDto>> getAllTerms(Pageable pageable) {
        return CommonResponse.success(termsService.getAllTerms(pageable));
    }
    @GetMapping("/latest")
    @Operation(summary = "최신 약관 목록 조회", description = "약관 제목별 최신 버전만 조회합니다.")
    public CommonResponse<TermsListResponseDto> getAllLatestTerms() {
        return CommonResponse.success(termsService.getAllLatestTerms());
    }

    @GetMapping("/{title}/{version}")
    @Operation(summary = "특정 약관 조회 API", description = "약관제목과 버전으로 특정 약관을 가져옵니다.")
    public CommonResponse<TermsResponseDto> getTermsByTitleAndVersion(
            @PathVariable String title,
            @PathVariable Integer version) {
        return CommonResponse.success(termsService.getTermsByTitleAndVersion(title, version));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "약관 등록 API", description = "신규 약관을 등록합니다.")
    public void createTerms(@Valid @RequestBody TermsRequestDto requestDto) {
        termsService.createTerms(requestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{title}/{version}")
    @Operation(summary = "약관 수정 API", description = "특정 약관의 내용을 수정합니다.")
    public void updateTerms(
            @PathVariable String title,
            @PathVariable Integer version,
            @Valid @RequestBody TermsRequestDto requestDto) {
        termsService.updateTerms(title, version, requestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{title}/{version}")
    @Operation(summary = "약관 삭제 API", description = "특정 약관을 삭제합니다.")
    public void deleteTerms(@PathVariable String title, @PathVariable Integer version) {
        termsService.deleteTerms(title, version);
    }

}