package com.ssafy.auction_service.domain.variety.repository;

import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.Variety;
import com.ssafy.auction_service.domain.variety.VarietyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VarietyRepository extends JpaRepository<Variety, String> {

    String NO_SUCH_VARIETY = "등록되지 않은 품종입니다.";

    @Query("select v.code from Variety v where v.info=:info")
    Optional<String> findCodeByVariety(VarietyInfo info);

    int countByInfoPlantCategory(PlantCategory plantCategory);
}
