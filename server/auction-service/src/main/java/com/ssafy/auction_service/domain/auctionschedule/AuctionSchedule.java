package com.ssafy.auction_service.domain.auctionschedule;

import com.ssafy.auction_service.domain.BaseEntity;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.Variety;
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
    private AuctionStatus auctionStatus;

    @Builder
    private AuctionSchedule(boolean isDeleted, Long createdBy, Long lastModifiedBy, AuctionInfo auctionInfo, LocalDateTime auctionStartDateTime, AuctionStatus auctionStatus) {
        super(isDeleted, createdBy, lastModifiedBy);
        this.auctionInfo = auctionInfo;
        this.auctionStartDateTime = auctionStartDateTime;
        this.auctionStatus = auctionStatus;
    }

    public static AuctionSchedule of(boolean isDeleted, Long createdBy, Long lastModifiedBy, AuctionInfo auctionInfo, LocalDateTime auctionStartDateTime, AuctionStatus auctionStatus) {
        return new AuctionSchedule(isDeleted, createdBy, lastModifiedBy, auctionInfo, auctionStartDateTime, auctionStatus);
    }

    public static AuctionSchedule create(Long createdBy, PlantCategory plantCategory, JointMarket jointMarket, String auctionDescription, LocalDateTime auctionStartDateTime) {
        AuctionInfo auctionInfo = AuctionInfo.of(plantCategory, jointMarket, auctionDescription);
        return of(false, createdBy, createdBy, auctionInfo, auctionStartDateTime, AuctionStatus.INIT);
    }

    public void ready(Long memberId) {
        modifyAuctionStatus(memberId, AuctionStatus.READY);
    }

    public void progress(Long memberId) {
        modifyAuctionStatus(memberId, AuctionStatus.PROGRESS);
    }

    public void complete(Long memberId) {
        modifyAuctionStatus(memberId, AuctionStatus.COMPLETE);
    }

    public boolean isInit() {
        return auctionStatus == AuctionStatus.INIT;
    }

    public boolean isNotInit() {
        return !isInit();
    }

    public boolean isReady() {
        return auctionStatus == AuctionStatus.READY;
    }

    public boolean isProgress() {
        return auctionStatus == AuctionStatus.PROGRESS;
    }

    public boolean isComplete() {
        return auctionStatus == AuctionStatus.COMPLETE;
    }

    public boolean isRegisteredVarietyBy(Variety variety) {
        return variety.plantCategoryEquals(auctionInfo.getPlantCategory());
    }

    public boolean isNotRegisteredVarietyBy(Variety variety) {
        return !isRegisteredVarietyBy(variety);
    }

    public String getPlantCategoryDescription() {
        return auctionInfo.getPlantCategory().getDescription();
    }

    public String getKoreanJointMarket() {
        return auctionInfo.getJointMarket().getKorean();
    }

    private void modifyAuctionStatus(Long memberId, AuctionStatus auctionStatus) {
        updateModifiedBy(memberId);
        this.auctionStatus = auctionStatus;
    }
}
