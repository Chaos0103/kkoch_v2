package com.kkoch.admin.domain.bidresult.repository;

import com.kkoch.admin.domain.bidresult.repository.dto.BidResultSearchCond;
import com.kkoch.admin.domain.bidresult.repository.dto.BidResultDto;
import com.kkoch.admin.domain.bidresult.repository.dto.BidResults;
import com.kkoch.admin.domain.bidresult.repository.response.BidResultResponse;
import com.kkoch.admin.domain.variety.PlantCategory;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.kkoch.admin.domain.auctionvariety.QAuctionVariety.auctionVariety;
import static com.kkoch.admin.domain.bidresult.QBidResult.bidResult;
import static com.kkoch.admin.domain.variety.QVariety.variety;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class BidResultQueryRepository {

    private final JPAQueryFactory queryFactory;

    public BidResultQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Long> findAllIdByCond(BidResultSearchCond cond, Pageable pageable) {
        return queryFactory
            .select(bidResult.id)
            .from(bidResult)
            .join(bidResult.auctionVariety, auctionVariety)
            .join(auctionVariety.variety, variety)
            .where(
                bidResult.bidDateTime.between(cond.getStartDateTime(), cond.getEndDateTime()),
                eqPlantCategory(cond.getPlantCategory()),
                eqItemName(cond.getItemName()),
                eqVarietyName(cond.getVarietyName()),
                eqRegion(cond.getRegion())
            )
            .orderBy(bidResult.bidDateTime.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();
    }

    public List<BidResultResponse> findAllByIdIn(List<Long> ids) {
        return queryFactory
            .select(
                Projections.fields(
                    BidResultResponse.class,
                    variety.plantCategory,
                    variety.itemName,
                    variety.varietyName,
                    auctionVariety.auctionVarietyInfo.grade,
                    auctionVariety.auctionVarietyInfo.plantCount,
                    bidResult.bidPrice,
                    bidResult.bidDateTime,
                    auctionVariety.shippingInfo.region
                )
            )
            .from(bidResult)
            .join(bidResult.auctionVariety, auctionVariety)
            .join(auctionVariety.variety, variety)
            .where(bidResult.id.in(ids))
            .orderBy(bidResult.bidDateTime.desc())
            .fetch();
    }

    public int countByCond(BidResultSearchCond cond) {
        return queryFactory
            .select(bidResult.id)
            .from(bidResult)
            .join(bidResult.auctionVariety, auctionVariety)
            .join(auctionVariety.variety, variety)
            .where(
                bidResult.bidDateTime.between(cond.getStartDateTime(), cond.getEndDateTime()),
                eqPlantCategory(cond.getPlantCategory()),
                eqItemName(cond.getItemName()),
                eqVarietyName(cond.getVarietyName()),
                eqRegion(cond.getRegion())
            )
            .fetch()
            .size();
    }

    public BidResults findAllByBidDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<BidResultDto> bidResults = queryFactory
            .select(
                Projections.fields(
                    BidResultDto.class,
                    bidResult.auctionVariety.variety.code.as("varietyCode"),
                    bidResult.auctionVariety.auctionVarietyInfo.grade,
                    bidResult.auctionVariety.auctionVarietyInfo.plantCount,
                    bidResult.bidPrice
                )
            )
            .from(bidResult)
            .join(bidResult.auctionVariety, auctionVariety)
            .where(
                bidResult.isDeleted.isFalse(),
                bidResult.bidDateTime.between(startDateTime, endDateTime)
            )
            .fetch();
        return BidResults.of(bidResults);
    }

    private BooleanExpression eqPlantCategory(PlantCategory plantCategory) {
        return plantCategory == null ? null : variety.plantCategory.eq(plantCategory);
    }

    private BooleanExpression eqItemName(String itemName) {
        return hasText(itemName) ? variety.itemName.eq(itemName) : null;
    }

    private BooleanExpression eqVarietyName(String varietyName) {
        return hasText(varietyName) ? variety.varietyName.eq(varietyName) : null;
    }

    private BooleanExpression eqRegion(String region) {
        return hasText(region) ? auctionVariety.shippingInfo.region.eq(region) : null;
    }
}
