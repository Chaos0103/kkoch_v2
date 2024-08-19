package com.ssafy.board_service.domain.notice;

import com.ssafy.board_service.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private int id;

    @Column(nullable = false, length = 50)
    private String noticeTitle;

    @Lob
    @Column(nullable = false, columnDefinition = "text")
    private String noticeContent;

    @Column(nullable = false)
    private LocalDateTime toFixedDateTime;

    @Builder
    private Notice(boolean isDeleted, Long createdBy, Long lastModifiedBy, String noticeTitle, String noticeContent, LocalDateTime toFixedDateTime) {
        super(isDeleted, createdBy, lastModifiedBy);
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.toFixedDateTime = toFixedDateTime;
    }

    public static Notice of(boolean isDeleted, Long createdBy, Long lastModifiedBy, String noticeTitle, String noticeContent, LocalDateTime toFixedDateTime) {
        return new Notice(isDeleted, createdBy, lastModifiedBy, noticeTitle, noticeContent, toFixedDateTime);
    }

    public static Notice create(Long createdBy, String noticeTitle, String noticeContent, LocalDateTime toFixedDateTime) {
        return of(false, createdBy, createdBy, noticeTitle, noticeContent, toFixedDateTime);
    }
}
