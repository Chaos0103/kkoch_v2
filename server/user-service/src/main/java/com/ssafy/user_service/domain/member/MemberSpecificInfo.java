package com.ssafy.user_service.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class MemberSpecificInfo {

    @Column(unique = true, nullable = false, columnDefinition = "char(36)", length = 36)
    private final String memberKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private final Role role;

    @Builder
    private MemberSpecificInfo(String memberKey, Role role) {
        this.memberKey = memberKey;
        this.role = role;
    }

    public static MemberSpecificInfo of(String memberKey, Role role) {
        return new MemberSpecificInfo(memberKey, role);
    }
}
