package com.kkoch.user.api.service.pointlog;

import com.kkoch.user.api.service.pointlog.request.PointLogCreateServiceRequest;
import com.kkoch.user.api.service.pointlog.response.PointLogCreateResponse;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import com.kkoch.user.domain.pointlog.PointLog;
import com.kkoch.user.domain.pointlog.repository.PointLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class PointLogService {

    private final PointLogRepository pointLogRepository;
    private final MemberRepository memberRepository;

    public PointLogCreateResponse createPointLog(String memberKey, PointLogCreateServiceRequest request) {
        Member member = findMemberBy(memberKey);

        PointLog savedPointLog = savePointLog(member, request);

        return PointLogCreateResponse.of(savedPointLog, member.getPoint().getValue());
    }

    private Member findMemberBy(String memberKey) {
        return memberRepository.findByMemberKey(memberKey)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 회원입니다."));
    }

    private PointLog savePointLog(Member member, PointLogCreateServiceRequest request) {
        PointLog pointLog = request.toEntity(member);
        return pointLogRepository.save(pointLog);
    }
}
