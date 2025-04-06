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
import org.springframework.dao.DataIntegrityViolationException;
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

    public void updateTerms(String title, TermsRequestDto dto) {
        // 해당 title의 최신 버전 조회
        Integer latestVersion = termsRepository.findMaxVersionByTitle(title)
                .orElse(0); // 없으면 첫 버전

        int newVersion = latestVersion + 1;

        TermsId newId = new TermsId(title, newVersion);

        // 새로운 약관 생성
        Terms newTerms = new Terms();
        newTerms.setId(newId);
        newTerms.setContent(dto.content());
        newTerms.setRequired(dto.required());

        // insert 시도 → 동시 충돌 시 DB 제약조건(PK) 위반 발생
        try {
            termsRepository.save(newTerms);
        } catch (DataIntegrityViolationException e) {
            // 다른 관리자가 같은 버전으로 먼저 저장한 경우 충돌
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