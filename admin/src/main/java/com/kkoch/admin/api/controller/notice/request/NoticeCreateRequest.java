package com.kkoch.admin.api.controller.notice.request;

import com.kkoch.admin.api.service.notice.dto.NoticeCreateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class NoticeCreateRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Builder
    private NoticeCreateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public NoticeCreateServiceRequest toServiceRequest() {
        return NoticeCreateServiceRequest.of(title, content);
    }
}
