package com.kkoch.user.domain.alarm.repository;

import com.kkoch.user.api.controller.alarm.response.AlarmResponse;
import com.kkoch.user.domain.alarm.Alarm;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kkoch.user.domain.alarm.QAlarm.alarm;
import static com.kkoch.user.domain.member.QMember.member;

@Repository
public class AlarmQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AlarmQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Alarm> findAllByMemberKey(String memberKey) {
        return queryFactory
            .selectFrom(alarm)
            .join(alarm.member, member)
            .where(
                alarm.isDeleted.isFalse(),
                alarm.isOpened.isFalse(),
                alarm.member.memberKey.eq(memberKey)
            )
            .fetch();
    }

    public List<AlarmResponse> findTop10ByMemberKey(String memberKey) {
        return queryFactory
            .select(
                Projections.fields(AlarmResponse.class,
                    alarm.id.as("alarmId"),
                    alarm.content,
                    alarm.isOpened,
                    alarm.createdDateTime
                )
            )
            .from(alarm)
            .join(alarm.member, member)
            .where(
                alarm.isDeleted.isFalse(),
                alarm.member.memberKey.eq(memberKey)
            )
            .orderBy(alarm.createdDateTime.desc())
            .limit(10)
            .offset(0)
            .fetch();
    }
}
