package com.kkoch.user.domain.reservation.repository;

import com.kkoch.user.api.controller.reservation.response.ReservationForAuctionResponse;
import com.kkoch.user.domain.reservation.Reservation;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kkoch.user.domain.member.QMember.member;
import static com.kkoch.user.domain.reservation.QReservation.reservation;

@Repository
public class ReservationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ReservationQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Long> findAllIdByMemberKey(String memberKey, Pageable pageable) {
        return queryFactory
            .select(reservation.id)
            .from(reservation)
            .join(reservation.member, member)
            .where(
                isNotDeleted(),
                reservation.member.memberKey.eq(memberKey)
            )
            .orderBy(reservation.createdDateTime.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();
    }

    public List<Reservation> findAllByIdIn(List<Long> ids) {
        return queryFactory
            .selectFrom(reservation)
            .where(reservation.id.in(ids))
            .orderBy(reservation.createdDateTime.desc())
            .fetch();
    }

    public int countByMemberKey(String memberKey) {
        return queryFactory
            .select(reservation.id)
            .from(reservation)
            .join(reservation.member, member)
            .where(
                isNotDeleted(),
                reservation.member.memberKey.eq(memberKey)
            )
            .fetch()
            .size();
    }

    public ReservationForAuctionResponse findByPlantId(Long plantId) {
        return null;
    }

    private static BooleanExpression isNotDeleted() {
        return reservation.isDeleted.isFalse();
    }
}
