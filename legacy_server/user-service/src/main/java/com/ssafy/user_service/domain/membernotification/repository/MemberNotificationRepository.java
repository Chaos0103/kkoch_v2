package com.ssafy.user_service.domain.membernotification.repository;

import com.ssafy.user_service.domain.membernotification.MemberNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberNotificationRepository extends JpaRepository<MemberNotification, Long> {

    List<MemberNotification> findAllByIdInAndIsOpenedFalse(List<Long> ids);

    List<MemberNotification> findAllByIdInAndIsDeletedFalse(List<Long> ids);
}
