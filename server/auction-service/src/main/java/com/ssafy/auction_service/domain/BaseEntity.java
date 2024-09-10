package com.ssafy.auction_service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity extends TimeBaseEntity {

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Long createdBy;

    @LastModifiedBy
    @Column(nullable = false)
    private Long lastModifiedBy;

    protected BaseEntity(boolean isDeleted, Long createdBy, Long lastModifiedBy) {
        super(isDeleted);
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
    }
}
