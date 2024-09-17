package com.ssafy.trade_service.domain.bidinfo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bid {

    @Id
    private Long memberId;
    private List<BidInfo> infos;
    private LocalDateTime createdDateTime;

    @Builder
    private Bid(Long memberId, List<BidInfo> infos, LocalDateTime createdDateTime) {
        this.memberId = memberId;
        this.infos = infos;
        this.createdDateTime = createdDateTime;
    }

    public static Bid of(Long memberId, List<BidInfo> infos, LocalDateTime createdDateTime) {
        return new Bid(memberId, infos, createdDateTime);
    }
}
