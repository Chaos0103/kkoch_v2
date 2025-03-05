package com.ssafy.boardservice.api.controller.notice.request;

import com.ssafy.boardservice.api.service.notice.request.NoticeCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import static com.ssafy.boardservice.api.controller.BindingMessage.NoticeMessage.NOT_BLANK_CONTENT;
import static com.ssafy.boardservice.api.controller.BindingMessage.NoticeMessage.NOT_BLANK_TITLE;

@Getter
public class NoticeCreateRequest {

    @NotBlank(message = NOT_BLANK_TITLE)
    private final String title;

    @NotBlank(message = NOT_BLANK_CONTENT)
    private final String content;

    private final String toFixedDateTime;

    @Builder
    private NoticeCreateRequest(String title, String content, String toFixedDateTime) {
        this.title = title;
        this.content = content;
        this.toFixedDateTime = toFixedDateTime;
    }

    public NoticeCreateServiceRequest toServiceRequest() {
        return NoticeCreateServiceRequest.of(title, content, null);
    }

    @Override
    public String toString() {
        return String.format("title = %s, content = %s, toFixedDateTime = %s", title, content, toFixedDateTime);
    }
}
