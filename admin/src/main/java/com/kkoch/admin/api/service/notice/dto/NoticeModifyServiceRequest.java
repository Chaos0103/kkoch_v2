package com.kkoch.admin.api.service.notice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class NoticeModifyServiceRequest {

    private final String title;
    private final String content;

    @Builder
    private NoticeModifyServiceRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static NoticeModifyServiceRequest of(String title, String content) {
        return new NoticeModifyServiceRequest(title, content);
    }
}
