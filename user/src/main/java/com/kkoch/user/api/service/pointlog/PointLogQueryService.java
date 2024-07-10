package com.kkoch.user.api.service.pointlog;

import com.kkoch.user.api.PageResponse;
import com.kkoch.user.domain.pointlog.repository.PointLogQueryRepository;
import com.kkoch.user.domain.pointlog.repository.response.PointLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointLogQueryService {

    private final PointLogQueryRepository pointLogQueryRepository;

    public PageResponse<PointLogResponse> getPointLogs(String memberKey, Pageable pageable) {
        int total = pointLogQueryRepository.countByMemberKey(memberKey);

        List<Long> pointLogIds = pointLogQueryRepository.findAllIdByMemberKey(memberKey, pageable);
        if (pointLogIds.isEmpty()) {
            return PageResponse.empty(pageable, total);
        }

        List<PointLogResponse> content = pointLogQueryRepository.findAllByIdIn(pointLogIds);

        return PageResponse.create(content, pageable, total);
    }
}
