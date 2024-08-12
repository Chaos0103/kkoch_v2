package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.domain.member.repository.MemberQueryRepository;
import com.ssafy.user_service.domain.member.repository.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberQueryRepository memberQueryRepository;

    public MemberInfoResponse searchMemberInfo(String memberKey) {
        MemberInfoResponse content = memberQueryRepository.findByMemberKey(memberKey)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 회원입니다."));

        return content.toMasking();
    }
}
