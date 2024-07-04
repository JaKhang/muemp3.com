package com.mue.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractEntity {


    @LastModifiedDate
    protected Timestamp lastModifiedAt;

    @CreatedDate
    protected Timestamp createdAt;

    @Column(columnDefinition = "bit(1) default false")
    protected boolean deleted = Boolean.FALSE;

    public abstract UUID getId();

    public abstract String getName();

    public String getAlias(){
        return null;
    }
}


