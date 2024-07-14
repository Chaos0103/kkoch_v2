package com.kkoch.admin.domain.auctionschedule.repository;

import com.kkoch.admin.domain.auctionschedule.AuctionRoomStatus;
import com.kkoch.admin.domain.auctionschedule.repository.response.OpenedAuctionResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.kkoch.admin.domain.auctionschedule.QAuctionSchedule.auctionSchedule;

@Repository
public class AuctionScheduleQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AuctionScheduleQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<OpenedAuctionResponse> findByRoomStatusIsOpen() {
        OpenedAuctionResponse content = queryFactory
            .select(
                Projections.fields(
                    OpenedAuctionResponse.class,
                    auctionSchedule.id.as("auctionScheduleId"),
                    auctionSchedule.code,
                    auctionSchedule.roomStatus,
                    auctionSchedule.auctionDateTime
                )
            )
            .from(auctionSchedule)
            .where(
                isNotDeleted(),
                auctionSchedule.roomStatus.eq(AuctionRoomStatus.OPEN)
            )
            .orderBy(auctionSchedule.auctionDateTime.asc())
            .fetchFirst();

        return Optional.ofNullable(content);
    }

    private BooleanExpression isNotDeleted() {
        return auctionSchedule.isDeleted.isFalse();
    }
}
