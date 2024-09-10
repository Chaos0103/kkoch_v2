package com.ssafy.auction_service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity extends TimeBaseEntity {

    @Column(nullable = false, updatable = false)
    private Long createdBy;

    @Column(nullable = false)
    private Long lastModifiedBy;

    protected BaseEntity(boolean isDeleted, Long createdBy, Long lastModifiedBy) {
        super(isDeleted);
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
    }

    protected void updateModifiedBy(Long modifiedBy) {
        this.lastModifiedBy = modifiedBy;
    }

    protected void remove(Long modifiedBy) {
        super.remove();
        lastModifiedBy = modifiedBy;
    }
}
