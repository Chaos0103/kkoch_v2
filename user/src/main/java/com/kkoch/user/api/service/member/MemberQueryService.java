package com.kkoch.user.api.service.member;

import com.kkoch.user.domain.member.repository.MemberQueryRepository;
import com.kkoch.user.domain.member.repository.response.MemberInfoResponse;
import com.kkoch.user.domain.member.repository.response.MemberResponseForAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberQueryRepository memberQueryRepository;

    public boolean isUsedEmailBy(String email) {
        return memberQueryRepository.existByEmail(email);
    }

    public MemberInfoResponse getMemberInfoBy(String memberKey) {
        return memberQueryRepository.findMemberInfoByMemberKey(memberKey)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    public List<MemberResponseForAdmin> getUsers() {
        return memberQueryRepository.findAllUser();
    }
}
