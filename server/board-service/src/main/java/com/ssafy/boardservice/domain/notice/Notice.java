package com.ssafy.boardservice.domain.notice;

import com.ssafy.boardservice.domain.notice.vo.NoticeFixedDateTime;
import com.ssafy.boardservice.domain.notice.vo.NoticeTitle;
import com.ssafy.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Integer id;

    @Embedded
    private NoticeTitle title;

    @Lob
    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Embedded
    private NoticeFixedDateTime toFixedDateTime;

    @Builder
    private Notice(boolean isDeleted, Long createdBy, Long lastModifiedBy, NoticeTitle title, String content, NoticeFixedDateTime toFixedDateTime) {
        super(isDeleted, createdBy, lastModifiedBy);
        this.title = title;
        this.content = content;
        this.toFixedDateTime = toFixedDateTime;
    }

    public static Notice create(Long createdBy, NoticeTitle title, String content, NoticeFixedDateTime toFixedDateTime) {
        return Notice.builder()
            .isDeleted(false)
            .createdBy(createdBy)
            .lastModifiedBy(createdBy)
            .title(title)
            .content(content)
            .toFixedDateTime(toFixedDateTime)
            .build();
    }

    public boolean isFixed(LocalDateTime current) {
        return toFixedDateTime.isFixed(current);
    }

    public void modify(Long modifiedBy, NoticeTitle title, String content, NoticeFixedDateTime toFixedDateTime) {
        updateModifiedBy(modifiedBy);
        this.title = title;
        this.content = content;
        this.toFixedDateTime = toFixedDateTime;
    }

    public void remove(Long modifiedBy) {
        updateModifiedBy(modifiedBy);
        remove();
    }
}
