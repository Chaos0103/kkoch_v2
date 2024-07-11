package com.kkoch.admin.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import static lombok.AccessLevel.PROTECTED;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@NoArgsConstructor(access = PROTECTED)
public abstract class BaseEntity extends TimeBaseEntity {

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Integer createdBy;

    @LastModifiedBy
    private Integer lastModifiedBy;

    protected BaseEntity(boolean isDeleted, Integer createdBy, Integer lastModifiedBy) {
        super(isDeleted);
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
    }

    protected void modify(Integer lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
