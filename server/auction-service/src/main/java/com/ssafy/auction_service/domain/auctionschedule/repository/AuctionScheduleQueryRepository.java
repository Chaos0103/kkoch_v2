package com.ssafy.auction_service.domain.auctionschedule.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatus;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.auctionschedule.repository.dto.AuctionScheduleSearchCond;
import com.ssafy.auction_service.domain.auctionschedule.repository.response.AuctionScheduleResponse;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.auction_service.domain.auctionschedule.QAuctionSchedule.auctionSchedule;

@Repository
public class AuctionScheduleQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AuctionScheduleQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AuctionScheduleResponse> findByCond(AuctionScheduleSearchCond cond) {
        return queryFactory
            .select(
                Projections.fields(
                    AuctionScheduleResponse.class,
                    auctionSchedule.id,
                    auctionSchedule.auctionInfo.plantCategory,
                    auctionSchedule.auctionInfo.jointMarket,
                    auctionSchedule.auctionInfo.auctionStartDateTime,
                    auctionSchedule.auctionStatus
                )
            )
            .from(auctionSchedule)
            .where(
                auctionSchedule.isDeleted.isFalse(),
                auctionSchedule.auctionStatus.in(AuctionStatus.getSearchableStatus()),
                eqPlantCategory(cond.getPlantCategory()),
                eqJointMarker(cond.getJointMarket())
            )
            .orderBy(auctionSchedule.auctionInfo.auctionStartDateTime.desc())
            .fetch();
    }

    private BooleanExpression eqPlantCategory(PlantCategory plantCategory) {
        return plantCategory == null ? null : auctionSchedule.auctionInfo.plantCategory.eq(plantCategory);
    }

    private BooleanExpression eqJointMarker(JointMarket jointMarket) {
        return jointMarket == null ? null : auctionSchedule.auctionInfo.jointMarket.eq(jointMarket);
    }
}
