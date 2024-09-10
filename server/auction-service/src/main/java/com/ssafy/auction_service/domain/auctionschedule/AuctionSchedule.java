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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private AuctionStatus auctionStatus;

    @Lob
    @Column(columnDefinition = "text")
    private String auctionDescription;

    @Builder
    private AuctionSchedule(boolean isDeleted, Long createdBy, Long lastModifiedBy, AuctionInfo auctionInfo, AuctionStatus auctionStatus, String auctionDescription) {
        super(isDeleted, createdBy, lastModifiedBy);
        this.auctionInfo = auctionInfo;
        this.auctionStatus = auctionStatus;
        this.auctionDescription = auctionDescription;
    }

    public static AuctionSchedule of(boolean isDeleted, Long createdBy, Long lastModifiedBy, AuctionInfo auctionInfo, AuctionStatus auctionStatus, String auctionDescription) {
        return new AuctionSchedule(isDeleted, createdBy, lastModifiedBy, auctionInfo, auctionStatus, auctionDescription);
    }

    public static AuctionSchedule create(PlantCategory plantCategory, JointMarket jointMarket, LocalDateTime auctionStartDateTime, String auctionDescription) {
        AuctionInfo auctionInfo = AuctionInfo.of(plantCategory, jointMarket, auctionStartDateTime);
        return of(false, null, null, auctionInfo, AuctionStatus.INIT, auctionDescription);
    }

    public void modify(LocalDateTime auctionStartDateTime, String auctionDescription) {
        auctionInfo = getModifiedAuctionInfo(auctionStartDateTime);
        this.auctionDescription = auctionDescription;
    }

    public void ready() {
        modifyAuctionStatus(AuctionStatus.READY);
    }

    public void progress() {
        modifyAuctionStatus(AuctionStatus.PROGRESS);
    }

    public void complete() {
        modifyAuctionStatus(AuctionStatus.COMPLETE);
    }

    @Override
    public void remove() {
        super.remove();
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

    public boolean isModifiable() {
        return isInit();
    }

    public boolean isNotModifiable() {
        return !isModifiable();
    }

    public boolean isRemovable() {
        return isInit();
    }

    public boolean isNotRemovable() {
        return !isRemovable();
    }

    public String getPlantCategoryDescription() {
        return auctionInfo.getPlantCategory().getDescription();
    }

    public String getKoreanJointMarket() {
        return auctionInfo.getJointMarket().getKorean();
    }

    public AuctionInfo getModifiedAuctionInfo(LocalDateTime auctionStartDateTime) {
        return auctionInfo.withAuctionStartDateTime(auctionStartDateTime);
    }

    private void modifyAuctionStatus(AuctionStatus auctionStatus) {
        this.auctionStatus = auctionStatus;
    }
}
