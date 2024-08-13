package com.ssafy.user_service.domain.membernotification.repository;

import com.ssafy.user_service.domain.membernotification.MemberNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberNotificationRepository extends JpaRepository<MemberNotification, Long> {
}
