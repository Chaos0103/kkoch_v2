package com.ssafy.board_service.api.controller.notice.request;

import com.ssafy.board_service.api.service.notice.request.NoticeModifyServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeModifyRequest {

    @NotBlank(message = "공지사항 제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "공지사항 내용을 입력해주세요.")
    private String content;

    private String toFixedDateTime;

    @Builder
    private NoticeModifyRequest(String title, String content, String toFixedDateTime) {
        this.title = title;
        this.content = content;
        this.toFixedDateTime = toFixedDateTime;
    }

    public NoticeModifyServiceRequest toServiceRequest() {
        return NoticeModifyServiceRequest.of(title, content, toFixedDateTime);
    }
}
