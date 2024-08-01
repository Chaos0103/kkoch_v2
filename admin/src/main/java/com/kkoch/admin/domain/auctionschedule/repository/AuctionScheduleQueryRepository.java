package com.kkoch.admin.domain.auctionschedule.repository;

import com.kkoch.admin.domain.auctionschedule.AuctionRoomStatus;
import com.kkoch.admin.domain.auctionschedule.repository.response.AuctionDateTimeVo;
import com.kkoch.admin.domain.auctionschedule.repository.response.AuctionScheduleResponse;
import com.kkoch.admin.domain.auctionschedule.repository.response.OpenedAuctionResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.kkoch.admin.domain.auctionschedule.QAuctionSchedule.auctionSchedule;

@Repository
public class AuctionScheduleQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AuctionScheduleQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Integer> findAllIdByCond(Pageable pageable) {
        return queryFactory
            .select(auctionSchedule.id)
            .from(auctionSchedule)
            .where(isNotDeleted())
            .orderBy(auctionSchedule.createdDateTime.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();
    }

    public List<AuctionScheduleResponse> findAllByIdIn(List<Integer> ids) {
        return queryFactory
            .select(
                Projections.fields(
                    AuctionScheduleResponse.class,
                    auctionSchedule.id.as("auctionScheduleId"),
                    auctionSchedule.plantCategory,
                    auctionSchedule.roomStatus,
                    auctionSchedule.auctionDateTime,
                    auctionSchedule.createdDateTime
                )
            )
            .from(auctionSchedule)
            .where(auctionSchedule.id.in(ids))
            .orderBy(auctionSchedule.createdDateTime.desc())
            .fetch();
    }

    public Optional<OpenedAuctionResponse> findByRoomStatusIsOpen() {
        OpenedAuctionResponse content = queryFactory
            .select(
                Projections.fields(
                    OpenedAuctionResponse.class,
                    auctionSchedule.id.as("auctionScheduleId"),
                    auctionSchedule.plantCategory,
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

    public int countByCond() {
        return queryFactory
            .select(auctionSchedule.id)
            .from(auctionSchedule)
            .where(isNotDeleted())
            .fetch()
            .size();
    }

    public List<AuctionDateTimeVo> findAllAuctionDataTimeByIdIn(List<Integer> ids) {
        return queryFactory
            .select(
                Projections.fields(
                    AuctionDateTimeVo.class,
                    auctionSchedule.id,
                    auctionSchedule.auctionDateTime
                )
            )
            .from(auctionSchedule)
            .where(
                auctionSchedule.id.in(ids)
            )
            .fetch();
    }

    private BooleanExpression isNotDeleted() {
        return auctionSchedule.isDeleted.isFalse();
    }
}
