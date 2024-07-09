package com.kkoch.user.domain.member.repository.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseForAdmin {

    private Long memberId;
    private String email;
    private String name;
    private String tel;
    private String businessNumber;
    private long point;
    private boolean isDeleted;

    @Builder
    private MemberResponseForAdmin(Long memberId, String email, String name, String tel, String businessNumber, long point, boolean isDeleted) {
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.tel = tel;
        this.businessNumber = businessNumber;
        this.point = point;
        this.isDeleted = isDeleted;
    }
}
