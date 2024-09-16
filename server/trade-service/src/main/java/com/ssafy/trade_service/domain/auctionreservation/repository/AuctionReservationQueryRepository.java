package com.ssafy.trade_service.domain.auctionreservation.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.trade_service.domain.auctionreservation.repository.response.AuctionReservationResponse;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.trade_service.domain.auctionreservation.QAuctionReservation.auctionReservation;

@Repository
public class AuctionReservationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AuctionReservationQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AuctionReservationResponse> findAllByAuctionScheduleId(Long memberId, int auctionScheduleId) {
        return queryFactory
            .select(
                Projections.fields(
                    AuctionReservationResponse.class,
                    auctionReservation.id,
                    auctionReservation.reservationInfo.varietyCode,
                    auctionReservation.reservationInfo.plantGrade,
                    auctionReservation.reservationInfo.plantCount,
                    auctionReservation.reservationInfo.desiredPrice.value.as("desiredPrice")
                )
            )
            .from(auctionReservation)
            .where(
                auctionReservation.isDeleted.isFalse(),
                auctionReservation.memberId.eq(memberId),
                auctionReservation.auctionScheduleId.eq(auctionScheduleId)
            )
            .orderBy(auctionReservation.createdDateTime.asc())
            .fetch();
    }
}
