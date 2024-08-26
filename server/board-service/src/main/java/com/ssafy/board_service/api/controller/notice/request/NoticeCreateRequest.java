package com.ssafy.board_service.api.controller.notice.request;

import com.ssafy.board_service.api.service.notice.request.NoticeCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.board_service.api.controller.notice.message.NoticeBindingMessage.NOT_BLANK_CONTENT;
import static com.ssafy.board_service.api.controller.notice.message.NoticeBindingMessage.NOT_BLANK_TITLE;

@Getter
@NoArgsConstructor
public class NoticeCreateRequest {

    @NotBlank(message = NOT_BLANK_TITLE)
    private String title;

    @NotBlank(message = NOT_BLANK_CONTENT)
    private String content;

    private String toFixedDateTime;

    @Builder
    private NoticeCreateRequest(String title, String content, String toFixedDateTime) {
        this.title = title;
        this.content = content;
        this.toFixedDateTime = toFixedDateTime;
    }

    public NoticeCreateServiceRequest toServiceRequest() {
        return NoticeCreateServiceRequest.of(title, content, toFixedDateTime);
    }
}
