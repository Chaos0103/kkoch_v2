package com.kkoch.admin.api.service.notice.dto;

import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.notice.Notice;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class NoticeCreateServiceRequest {

    private final String title;
    private final String content;

    @Builder
    private NoticeCreateServiceRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static NoticeCreateServiceRequest of(String title, String content) {
        return new NoticeCreateServiceRequest(title, content);
    }

    public Notice toEntity(Admin admin) {
        return Notice.create(title, content, admin);
    }
}
