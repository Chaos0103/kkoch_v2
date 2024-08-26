package com.ssafy.auction_service.domain.variety.repository;

import com.ssafy.auction_service.domain.variety.Variety;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VarietyRepository extends JpaRepository<Variety, String> {
}
