package com.danahub.zipitda.community.service;

import com.danahub.zipitda.community.domain.Bookmark;
import com.danahub.zipitda.community.dto.BookmarkRequestDto;
import com.danahub.zipitda.community.dto.BookmarkResponseDto;
import com.danahub.zipitda.community.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public BookmarkResponseDto toggleBookmark(Long userId, BookmarkRequestDto requestDto) {
        // DB에서 userId + targetType + targetId 조합으로 북마크 검색
        Bookmark existing = bookmarkRepository
                .findByUserIdAndTargetTypeAndTargetId(userId, requestDto.targetType(), requestDto.targetId());

        boolean bookmarked;
        if (existing != null) {
            // 이미 북마크 → 삭제
            bookmarkRepository.delete(existing);
            bookmarked = false;
        } else {
            // 미존재 → 새로 등록
            Bookmark newBookmark = Bookmark.builder()
                    .userId(userId)
                    .targetType(requestDto.targetType())
                    .targetId(requestDto.targetId())
                    .build();
            bookmarkRepository.save(newBookmark);
            bookmarked = true;
        }

        // 현재 target에 대한 북마크 개수 조회
        int count = bookmarkRepository.countByTargetTypeAndTargetId(requestDto.targetType(), requestDto.targetId());
        return new BookmarkResponseDto(requestDto.targetType(), requestDto.targetId(), count, bookmarked);
    }
}