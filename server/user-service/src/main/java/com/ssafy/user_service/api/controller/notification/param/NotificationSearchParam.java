package com.ssafy.user_service.api.controller.notification.param;

import com.ssafy.user_service.api.service.StringValidate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationSearchParam {

    private String page = "1";
    private String category;

    @Builder
    private NotificationSearchParam(String page, String category) {
        this.page = page;
        this.category = category;
    }

    public int toNumberPage() {
        StringValidate strPage = StringValidate.of(this.page);
        if (strPage.isBlank() || strPage.isNotNumber()) {
            return 0;
        }

        int intPage = Integer.parseInt(this.page);
        if (intPage < 1) {
            return 0;
        }

        return intPage - 1;
    }
}
