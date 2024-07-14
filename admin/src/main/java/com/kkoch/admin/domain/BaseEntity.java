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
    private int createdBy;

    @LastModifiedBy
    private int lastModifiedBy;

    protected BaseEntity(boolean isDeleted, int createdBy, int lastModifiedBy) {
        super(isDeleted);
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
    }

    protected void modify(int lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    protected void remove(int lastModifiedBy) {
        super.remove();
        this.lastModifiedBy = lastModifiedBy;
    }
}
