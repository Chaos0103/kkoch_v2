package com.ssafy.board_service.domain.notice.repository;

import com.ssafy.board_service.domain.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {
}
