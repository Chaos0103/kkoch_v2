package com.ssafy.user_service.domain.member.repository.response;

import com.ssafy.user_service.api.service.member.Masking;
import com.ssafy.user_service.common.exception.AppException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.user_service.common.util.StringUtils.isBlank;

@Getter
@NoArgsConstructor(force = true)
public class MemberInfoResponse {

    private final String email;
    private final String name;
    private final String tel;

    @Builder
    private MemberInfoResponse(String email, String name, String tel) {
        this.email = email;
        this.name = name;
        this.tel = tel;
    }

    public MemberInfoResponse toMasking() {
        if (isBlank(email) || isBlank(name) || isBlank(tel)) {
            throw new AppException("잠시 후 다시 시도해주세요.");
        }

        String maskingEmail = Masking.maskingEmail(email);
        String maskingTel = Masking.maskingTel(tel);

        return new MemberInfoResponse(maskingEmail, name, maskingTel);
    }
}
