package com.ssafy.auction_service.domain.variety.repository;

import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.Variety;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VarietyRepository extends JpaRepository<Variety, String> {

    @Query("select v.code from Variety v where v.plantCategory=:plantCategory and v.itemName=:itemName and v.varietyName=:varietyName")
    Optional<String> findCodeByVariety(PlantCategory plantCategory, String itemName, String varietyName);

    int countByPlantCategory(PlantCategory plantCategory);
}
