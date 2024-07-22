package com.kkoch.admin.domain.stats.repository;

import com.kkoch.admin.domain.stats.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Long> {
}
