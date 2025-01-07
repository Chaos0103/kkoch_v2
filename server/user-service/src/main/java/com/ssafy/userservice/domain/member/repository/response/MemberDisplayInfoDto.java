package com.ssafy.userservice.domain.member.repository.response;

import com.ssafy.common.global.exception.AppException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.common.global.utils.StringUtils.isBlank;

@Getter
@NoArgsConstructor(force = true)
public class MemberDisplayInfoDto {

    private final String email;
    private final String name;
    private final String tel;

    @Builder
    private MemberDisplayInfoDto(String email, String name, String tel) {
        this.email = email;
        this.name = name;
        this.tel = tel;
    }

    public MemberDisplayInfoDto toMasking() {
        if (isBlank(email) || isBlank(name) || isBlank(tel)) {
            throw new AppException("잠시 후 다시 시도해주세요.");
        }

        //TODO: 기능 구현
//        String maskingEmail = Masking.maskingEmail(email);
//        String maskingTel = Masking.maskingTel(tel);

        return new MemberDisplayInfoDto(email, name, tel);
    }
}
