package com.kkoch.admin.domain.auctionvariety.repository;

import com.kkoch.admin.domain.auctionvariety.QAuctionVariety;
import com.kkoch.admin.domain.auctionvariety.repository.response.AuctionVarietyResponse;
import com.kkoch.admin.domain.variety.QVariety;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kkoch.admin.domain.auctionvariety.QAuctionVariety.auctionVariety;
import static com.kkoch.admin.domain.variety.QVariety.variety;

@Repository
public class AuctionVarietyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AuctionVarietyQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AuctionVarietyResponse> findByAuctionScheduleId(int auctionScheduleId) {
        return queryFactory
            .select(
                Projections.fields(
                    AuctionVarietyResponse.class,
                    auctionVariety.id.as("auctionVarietyId"),
                    auctionVariety.auctionNumber,
                    variety.code.as("varietyCode"),
                    variety.plantCategory,
                    variety.itemName,
                    variety.varietyName,
                    auctionVariety.auctionVarietyInfo.plantCount,
                    auctionVariety.auctionVarietyInfo.startPrice,
                    auctionVariety.auctionVarietyInfo.grade,
                    auctionVariety.shippingInfo.region,
                    auctionVariety.shippingInfo.shipper
                )
            )
            .from(auctionVariety)
            .join(auctionVariety.variety, variety)
            .where(
                auctionVariety.isDeleted.isFalse(),
                auctionVariety.auctionSchedule.id.eq(auctionScheduleId)
            )
            .orderBy(auctionVariety.auctionNumber.asc())
            .fetch();
    }
}
