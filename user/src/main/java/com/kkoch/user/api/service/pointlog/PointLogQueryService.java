package com.kkoch.user.api.service.pointlog;

import com.kkoch.user.domain.pointlog.repository.PointLogQueryRepository;
import com.kkoch.user.domain.pointlog.repository.response.PointLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointLogQueryService {

    private final PointLogQueryRepository pointLogQueryRepository;

    public Page<PointLogResponse> getPointLogs(String memberKey, Pageable pageable) {
        List<Long> pointLogIds = pointLogQueryRepository.findAllIdByMemberKey(memberKey, pageable);
        List<PointLogResponse> content = pointLogQueryRepository.findAllByIdIn(pointLogIds);
        int count = pointLogQueryRepository.countByMemberKey(memberKey);
        return new PageImpl<>(content, pageable, count);
    }
}
