package com.ssafy.auction_service.domain.variety.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.repository.cond.VarietySearchCond;
import com.ssafy.auction_service.domain.variety.repository.response.ItemNameResponse;
import com.ssafy.auction_service.domain.variety.repository.response.VarietyResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.auction_service.common.util.StringUtils.isBlank;
import static com.ssafy.auction_service.domain.variety.QVariety.variety;

@Repository
public class VarietyQueryRepository {

    private final JPQLQueryFactory queryFactory;

    public VarietyQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<VarietyResponse> findAllByCond(VarietySearchCond cond, Pageable pageable) {
        return queryFactory
            .select(
                Projections.fields(
                    VarietyResponse.class,
                    variety.code,
                    variety.info.plantCategory,
                    variety.info.itemName,
                    variety.info.varietyName
                )
            )
            .from(variety)
            .where(
                variety.isDeleted.isFalse(),
                variety.info.plantCategory.eq(cond.getPlantCategory()),
                eqItemName(cond.getItemName())
            )
            .orderBy(
                variety.info.itemName.asc(),
                variety.info.varietyName.asc()
            )
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();
    }

    public int countByCond(VarietySearchCond cond) {
        return queryFactory
            .select(variety.code)
            .from(variety)
            .where(
                variety.isDeleted.isFalse(),
                variety.info.plantCategory.eq(cond.getPlantCategory()),
                eqItemName(cond.getItemName())
            )
            .fetch()
            .size();
    }

    public List<ItemNameResponse> findItemNameByPlantCategory(PlantCategory plantCategory) {
        return null;
    }

    private BooleanExpression eqItemName(String itemName) {
        return isBlank(itemName) ? null : variety.info.itemName.eq(itemName);
    }
}
