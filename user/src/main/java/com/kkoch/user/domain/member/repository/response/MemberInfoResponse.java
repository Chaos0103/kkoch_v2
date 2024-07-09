package com.kkoch.user.domain.member.repository.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberInfoResponse {

    private String email;
    private String name;
    private String tel;
    private String businessNumber;

    @Builder
    private MemberInfoResponse(String email, String name, String tel, String businessNumber) {
        this.email = email;
        this.name = name;
        this.tel = tel;
        this.businessNumber = businessNumber;
    }
}
