package com.kkoch.admin.domain.stats.repository;

import com.kkoch.admin.domain.stats.repository.dto.StatsSearchCond;
import com.kkoch.admin.domain.stats.repository.response.StatsResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.kkoch.admin.domain.stats.QStats.stats;

@Repository
public class StatsQueryRepository {

    private final JPAQueryFactory queryFactory;

    public StatsQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<StatsResponse> findAllByCond(StatsSearchCond cond, LocalDate currentDate, String itemName, String varietyName) {
        return queryFactory
            .select(
                Projections.fields(
                    StatsResponse.class,
                    stats.price.avg.as("avgPrice"),
                    stats.price.max.as("maxPrice"),
                    stats.price.max.as("minPrice"),
                    stats.grade,
                    stats.plantCount,
                    Expressions.asString(itemName).as("itemName"),
                    Expressions.asString(varietyName).as("varietyName"),
                    stats.createdDateTime
                )
            )
            .from(stats)
            .where(
                stats.isDeleted.isFalse(),
                stats.varietyCode.eq(cond.getVarietyCode()),
                stats.statsDate.after(currentDate.minusDays(7))
            )
            .orderBy(stats.createdDateTime.desc())
            .fetch();
    }
}
