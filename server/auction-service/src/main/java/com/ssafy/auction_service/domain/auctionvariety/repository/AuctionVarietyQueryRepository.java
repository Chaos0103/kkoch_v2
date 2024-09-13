package com.ssafy.auction_service.domain.auctionvariety.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.auction_service.domain.auctionvariety.repository.response.AuctionVarietyResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.auction_service.domain.auctionvariety.QAuctionVariety.auctionVariety;
import static com.ssafy.auction_service.domain.variety.QVariety.variety;

@Repository
public class AuctionVarietyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AuctionVarietyQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AuctionVarietyResponse> findAllByAuctionScheduleId(int auctionScheduleId, Pageable pageable) {
        return queryFactory
            .select(
                Projections.fields(
                    AuctionVarietyResponse.class,
                    auctionVariety.id,
                    variety.code,
                    variety.info.plantCategory,
                    variety.info.itemName,
                    variety.info.varietyName,
                    auctionVariety.listingNumber,
                    auctionVariety.auctionPlant.plantGrade,
                    auctionVariety.auctionPlant.plantCount,
                    auctionVariety.auctionPlant.auctionStartPrice.value,
                    auctionVariety.shipment.region,
                    auctionVariety.shipment.shipper
                )
            )
            .from(auctionVariety)
            .join(auctionVariety.variety, variety)
            .where(
                auctionVariety.isDeleted.isFalse(),
                auctionVariety.auctionSchedule.id.eq(auctionScheduleId)
            )
            .orderBy(auctionVariety.listingNumber.asc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();
    }
}
