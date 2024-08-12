package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.domain.member.repository.MemberQueryRepository;
import com.ssafy.user_service.domain.member.repository.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberQueryRepository memberQueryRepository;

    public MemberInfoResponse searchMemberInfo(String memberKey) {
        return null;
    }
}
