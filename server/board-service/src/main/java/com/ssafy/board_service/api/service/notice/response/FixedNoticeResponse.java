package com.ssafy.board_service.api.service.notice.response;

import com.ssafy.board_service.domain.notice.repository.response.NoticeResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FixedNoticeResponse {

    private List<NoticeResponse> content;
    private int size;

    @Builder
    private FixedNoticeResponse(List<NoticeResponse> content) {
        this.content = content;
        this.size = content.size();
    }

    public static FixedNoticeResponse of(List<NoticeResponse> content) {
        return new FixedNoticeResponse(content);
    }
}
