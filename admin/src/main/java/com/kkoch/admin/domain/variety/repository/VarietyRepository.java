package com.kkoch.admin.domain.variety.repository;

import com.kkoch.admin.domain.variety.PlantCategory;
import com.kkoch.admin.domain.variety.Variety;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VarietyRepository extends JpaRepository<Variety, String> {

    int countByPlantCategory(PlantCategory plantCategory);
}
