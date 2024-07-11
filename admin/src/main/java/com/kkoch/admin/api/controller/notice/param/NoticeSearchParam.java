package com.kkoch.admin.api.controller.notice.param;

import com.kkoch.admin.domain.notice.repository.dto.NoticeSearchCond;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class NoticeSearchParam {

    @Positive(message = "페이지 번호을 올바르게 입력해주세요.")
    private int page = 1;

    private String keyword;

    @Builder
    private NoticeSearchParam(int page, String keyword) {
        this.page = page;
        this.keyword = keyword;
    }

    public Pageable getPageable() {
        return PageRequest.of(page - 1, 10);
    }

    public NoticeSearchCond getSearchCond() {
        return NoticeSearchCond.of(keyword);
    }
}
