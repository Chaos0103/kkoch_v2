package com.kkoch.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@NoArgsConstructor(access = PROTECTED)
public abstract class TimeBaseEntity {

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;

    protected TimeBaseEntity(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    protected void remove() {
        this.isDeleted = true;
    }
}
