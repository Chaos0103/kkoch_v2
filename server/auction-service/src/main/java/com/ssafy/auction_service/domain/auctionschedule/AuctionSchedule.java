package com.ssafy.auction_service.domain.auctionschedule;

import com.ssafy.auction_service.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_schedule_id")
    private Integer id;

    @Embedded
    private AuctionInfo auctionInfo;

    @Column(nullable = false)
    private LocalDateTime auctionStartDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private AuctionStatue auctionStatue;

    @Builder
    private AuctionSchedule(boolean isDeleted, Long createdBy, Long lastModifiedBy, AuctionInfo auctionInfo, LocalDateTime auctionStartDateTime, AuctionStatue auctionStatue) {
        super(isDeleted, createdBy, lastModifiedBy);
        this.auctionInfo = auctionInfo;
        this.auctionStartDateTime = auctionStartDateTime;
        this.auctionStatue = auctionStatue;
    }

    public static AuctionSchedule of(boolean isDeleted, Long createdBy, Long lastModifiedBy, AuctionInfo auctionInfo, LocalDateTime auctionStartDateTime, AuctionStatue auctionStatue) {
        return new AuctionSchedule(isDeleted, createdBy, lastModifiedBy, auctionInfo, auctionStartDateTime, auctionStatue);
    }
}
