package com.ssafy.userservice.domain.member.vo;

import com.ssafy.userservice.domain.member.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.UUID;

@Getter
@Embeddable
@EqualsAndHashCode
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

    public static MemberSpecificInfo create(Role role) {
        String memberKey = generateMemberKey();
        return of(memberKey, role);
    }

    public MemberSpecificInfo withRoleBusiness() {
        return of(memberKey, Role.BUSINESS);
    }

    private static String generateMemberKey() {
        return UUID.randomUUID().toString();
    }
}
