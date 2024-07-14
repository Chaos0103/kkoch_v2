package com.kkoch.admin.domain.variety.repository;

import com.kkoch.admin.domain.variety.PlantCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kkoch.admin.domain.variety.QVariety.variety;

@Repository
public class VarietyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public VarietyQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<String> findAllItemNameByPlantCategory(PlantCategory category) {
        return queryFactory
            .select(variety.itemName).distinct()
            .from(variety)
            .where(
                variety.isDeleted.isFalse(),
                variety.plantCategory.eq(category)
            )
            .orderBy(variety.itemName.asc())
            .fetch();
    }

    public List<String> findAllVarietyNameByItemName(String itemName) {
        return queryFactory
            .select(variety.varietyName)
            .from(variety)
            .where(
                variety.isDeleted.isFalse(),
                variety.itemName.eq(itemName)
            )
            .orderBy(variety.varietyName.asc())
            .fetch();
    }
}
