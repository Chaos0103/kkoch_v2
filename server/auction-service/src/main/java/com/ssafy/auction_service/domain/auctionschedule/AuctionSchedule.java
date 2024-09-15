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
    private AuctionSchedule(boolean isDeleted, AuctionInfo auctionInfo, AuctionStatus auctionStatus, String auctionDescription) {
        super(isDeleted);
        this.auctionInfo = auctionInfo;
        this.auctionStatus = auctionStatus;
        this.auctionDescription = auctionDescription;
    }

    public static AuctionSchedule of(boolean isDeleted, AuctionInfo auctionInfo, AuctionStatus auctionStatus, String auctionDescription) {
        return new AuctionSchedule(isDeleted, auctionInfo, auctionStatus, auctionDescription);
    }

    public static AuctionSchedule create(PlantCategory plantCategory, JointMarket jointMarket, LocalDateTime auctionStartDateTime, String auctionDescription) {
        AuctionInfo auctionInfo = AuctionInfo.of(plantCategory, jointMarket, auctionStartDateTime);
        return of(false, auctionInfo, AuctionStatus.INIT, auctionDescription);
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

    public boolean isReady() {
        return auctionStatus == AuctionStatus.READY;
    }

    public boolean isProgress() {
        return auctionStatus == AuctionStatus.PROGRESS;
    }

    public boolean isComplete() {
        return auctionStatus == AuctionStatus.COMPLETE;
    }

    public boolean isNotInit() {
        return !isInit();
    }

    public boolean canRegister(Variety variety) {
        return variety.plantCategoryEquals(auctionInfo.getPlantCategory());
    }

    public boolean cannotRegister(Variety variety) {
        return !canRegister(variety);
    }

    public boolean canModify() {
        return isInit();
    }

    public boolean cannotModify() {
        return !canModify();
    }

    public boolean canRemove() {
        return isInit();
    }

    public boolean cannotRemove() {
        return !canRemove();
    }

    public String getPlantCategoryDescription() {
        return auctionInfo.getPlantCategory().getText();
    }

    public String getJointMarketFullName() {
        return auctionInfo.getJointMarket().getFullName();
    }

    public AuctionInfo getModifiedAuctionInfo(LocalDateTime auctionStartDateTime) {
        return auctionInfo.withAuctionStartDateTime(auctionStartDateTime);
    }

    private void modifyAuctionStatus(AuctionStatus auctionStatus) {
        this.auctionStatus = auctionStatus;
    }
}
