package com.kkoch.admin.domain.auctionschedule;

import com.kkoch.admin.domain.BaseEntity;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.variety.PlantCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_schedule_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 13)
    private PlantCategory code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 5)
    private AuctionRoomStatus roomStatus;

    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime auctionDateTime;

    @Builder
    private AuctionSchedule(boolean isDeleted, int createdBy, int lastModifiedBy, PlantCategory code, AuctionRoomStatus roomStatus, LocalDateTime auctionDateTime) {
        super(isDeleted, createdBy, lastModifiedBy);
        this.code = code;
        this.roomStatus = roomStatus;
        this.auctionDateTime = auctionDateTime;
    }

    public static AuctionSchedule of(boolean isDeleted, int createdBy, int lastModifiedBy, PlantCategory code, AuctionRoomStatus status, LocalDateTime actionDateTime) {
        return new AuctionSchedule(isDeleted, createdBy, lastModifiedBy, code, status, actionDateTime);
    }

    public static AuctionSchedule create(PlantCategory code, LocalDateTime actionDateTime, Admin admin) {
        return of(false, admin.getId(), admin.getId(), code, AuctionRoomStatus.INIT, actionDateTime);
    }

    public void modify(PlantCategory code, LocalDateTime actionDateTime, Admin admin) {
        super.modify(admin.getId());
        this.code = code;
        this.auctionDateTime = actionDateTime;
    }

    public void ready(Admin admin) {
        modifyAuctionRoomStatus(admin, AuctionRoomStatus.READY);
    }

    public void open(Admin admin) {
        modifyAuctionRoomStatus(admin, AuctionRoomStatus.OPEN);
    }

    public void close(Admin admin) {
        modifyAuctionRoomStatus(admin, AuctionRoomStatus.CLOSE);
    }

    public void remove(Admin admin) {
        super.remove(admin.getId());
    }

    private void modifyAuctionRoomStatus(Admin admin, AuctionRoomStatus ready) {
        super.modify(admin.getId());
        this.roomStatus = ready;
    }
}
