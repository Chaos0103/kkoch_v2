package com.ssafy.trade_service.domain.auctionstatistics.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.trade_service.domain.auctionstatistics.repository.cond.AuctionStatisticsSearchCond;
import com.ssafy.trade_service.domain.auctionstatistics.repository.response.AuctionStatisticsResponse;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.trade_service.domain.auctionstatistics.QAuctionStatistics.auctionStatistics;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class AuctionStatisticsQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AuctionStatisticsQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AuctionStatisticsResponse> findAllByCond(String varietyCode, AuctionStatisticsSearchCond cond) {
        return queryFactory
            .select(
                Projections.fields(
                    AuctionStatisticsResponse.class,
                    auctionStatistics.varietyCode,
                    auctionStatistics.plantGrade,
                    auctionStatistics.plantCount,
                    auctionStatistics.calculatedResult.avg,
                    auctionStatistics.calculatedResult.max,
                    auctionStatistics.calculatedResult.min,
                    auctionStatistics.calculatedDate
                )
            )
            .from(auctionStatistics)
            .where(
                auctionStatistics.varietyCode.eq(varietyCode),
                auctionStatistics.calculatedDate.between(cond.getFrom(), cond.getTo()),
                eqPlantGrade(cond.getPlantGrade())
            )
            .orderBy(auctionStatistics.calculatedDate.desc())
            .fetch();
    }

    private BooleanExpression eqPlantGrade(String plantGrade) {
        return hasText(plantGrade) ? auctionStatistics.plantGrade.eq(plantGrade) : null;
    }
}
