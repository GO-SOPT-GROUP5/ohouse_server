package com.cds.ohouse.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    @LastModifiedDate
    private LocalDateTime lastModifiedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        lastModifiedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    @PreUpdate
    void preUpdate() {
        lastModifiedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
