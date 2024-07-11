package com.kkoch.admin.domain.admin.repository;

import com.kkoch.admin.api.controller.admin.LoginAdmin;
import com.kkoch.admin.api.controller.admin.response.AdminResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.kkoch.admin.domain.admin.QAdmin.admin;

@Repository
public class AdminQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AdminQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AdminResponse> getAdmins() {
        return null;
    }

    public Optional<LoginAdmin> getLoginAdmin(String loginId, String loginPw) {
        return Optional.empty();
    }
}
