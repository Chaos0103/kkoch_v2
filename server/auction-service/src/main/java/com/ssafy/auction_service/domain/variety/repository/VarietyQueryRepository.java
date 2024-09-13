package com.ssafy.auction_service.domain.variety.repository;

import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.auction_service.domain.variety.repository.cond.VarietySearchCond;
import com.ssafy.auction_service.domain.variety.repository.response.VarietyResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VarietyQueryRepository {

    private final JPQLQueryFactory queryFactory;

    public VarietyQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<VarietyResponse> findAllByCond(VarietySearchCond cond, Pageable pageable) {
        return null;
    }

    public int countByCond(VarietySearchCond cond) {
        return 0;
    }
}
