package com.kkoch.user.domain.pointlog.repository;

import com.kkoch.user.domain.pointlog.PointLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointLogRepository extends JpaRepository<PointLog, Long> {
}
