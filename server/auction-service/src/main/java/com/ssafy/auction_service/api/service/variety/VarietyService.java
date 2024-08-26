package com.ssafy.auction_service.api.service.variety;

import com.ssafy.auction_service.api.client.MemberServiceClient;
import com.ssafy.auction_service.api.service.variety.request.VarietyCreateServiceRequest;
import com.ssafy.auction_service.api.service.variety.response.VarietyCreateResponse;
import com.ssafy.auction_service.domain.variety.repository.VarietyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VarietyService {

    private final VarietyRepository varietyRepository;
    private final MemberServiceClient memberServiceClient;

    public VarietyCreateResponse createVariety(VarietyCreateServiceRequest request) {
        return null;
    }
}
