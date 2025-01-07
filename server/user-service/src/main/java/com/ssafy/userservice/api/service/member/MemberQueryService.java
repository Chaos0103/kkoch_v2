package com.ssafy.userservice.api.service.member;

import com.ssafy.common.global.exception.MemberException;
import com.ssafy.userservice.api.service.member.response.MemberDisplayInfoResponse;
import com.ssafy.userservice.api.service.member.response.MemberIdInnerClientResponse;
import com.ssafy.userservice.domain.member.repository.MemberQueryRepository;
import com.ssafy.userservice.domain.member.repository.response.MemberDisplayInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberQueryRepository memberQueryRepository;

    public MemberDisplayInfoResponse searchMemberInfo(String memberKey) {
        log.debug("회원 정보 조회 요청 [memberKey = {}]", memberKey);
        MemberDisplayInfoDto findMemberInfo = memberQueryRepository.findMemberDisplayInfoByMemberKey(memberKey)
            .orElseThrow(() -> MemberException.notFound(memberKey));

        return MemberDisplayInfoResponse.of(findMemberInfo);
    }

    public MemberIdInnerClientResponse searchMemberId(String memberKey) {
        log.debug("회원 기본키 조회 요청 [memberKey = {}]", memberKey);
        Long findMemberId = memberQueryRepository.findMemberIdByMemberKey(memberKey)
            .orElseThrow(() -> MemberException.notFound(memberKey));

        return MemberIdInnerClientResponse.of(findMemberId);
    }
}
