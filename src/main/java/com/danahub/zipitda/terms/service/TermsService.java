package com.danahub.zipitda.terms.service;

import com.danahub.zipitda.common.exception.ErrorType;
import com.danahub.zipitda.common.exception.ZipitdaException;
import com.danahub.zipitda.store.dto.ProductResponseDto;
import com.danahub.zipitda.terms.domain.Terms;
import com.danahub.zipitda.terms.domain.TermsId;
import com.danahub.zipitda.terms.dto.TermsListResponseDto;
import com.danahub.zipitda.terms.dto.TermsRequestDto;
import com.danahub.zipitda.terms.dto.TermsResponseDto;
import com.danahub.zipitda.terms.mapper.TermsMapper;
import com.danahub.zipitda.terms.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TermsService {

    private final TermsRepository termsRepository;

    public Page<TermsResponseDto> getAllTerms(Pageable pageable) {
        return termsRepository.findAll(pageable)
                .map(TermsResponseDto::fromEntity);
    }

    // 최신 약관 리스트 가져오기
    public TermsListResponseDto getAllLatestTerms() {
        List<Terms> latestTerms = termsRepository.findLatestTermsByTitle();
        List<TermsResponseDto> dtoList = latestTerms.stream()
                .map(TermsResponseDto::fromEntity)
                .toList();
        return TermsListResponseDto.from(dtoList);
    }

    // 특정 약관 가져오기 (복합키)
    public TermsResponseDto getTermsByTitleAndVersion(String title, Integer version) {
        TermsId termsId = new TermsId(title, version);
        Terms terms = termsRepository.findById(termsId)
                .orElseThrow(() -> new ZipitdaException(ErrorType.TERM_NOT_FOUND));
        return TermsResponseDto.fromEntity(terms);
    }

    // 약관 등록
    public void createTerms(TermsRequestDto requestDto) {
        TermsId termsId = new TermsId(requestDto.title(), requestDto.version());

        if (termsRepository.existsById(termsId)) {
            throw new ZipitdaException(ErrorType.TERM_ALREADY_EXISTS);
        }

        Terms terms = new Terms();
        terms.setId(termsId);
        terms.setContent(requestDto.content());
        terms.setRequired(requestDto.required());

        termsRepository.save(terms);
    }

    public void updateTerms(String title, Integer version, TermsRequestDto requestDto) {
        TermsId termsId = new TermsId(title, version);
        Terms terms = termsRepository.findById(termsId)
                .orElseThrow(() -> new ZipitdaException(ErrorType.TERM_NOT_FOUND));

        // 낙관적 락 적용 : 요청된 versionNumber와 DB 버전 비교
        if (!terms.getVersionNumber().equals(requestDto.versionNumber())) {
            throw new ZipitdaException(ErrorType.CONCURRENT_UPDATE_CONFLICT);
        }

        terms.setContent(requestDto.content());
        terms.setRequired(requestDto.required());

        try {
            termsRepository.save(terms);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new ZipitdaException(ErrorType.CONCURRENT_UPDATE_CONFLICT);
        }
    }

    // 약관 삭제
    public void deleteTerms(String title, Integer version) {
        TermsId termsId = new TermsId(title, version);
        Terms terms = termsRepository.findById(termsId)
                .orElseThrow(() -> new ZipitdaException(ErrorType.TERM_NOT_FOUND));

        termsRepository.delete(terms);
    }
}