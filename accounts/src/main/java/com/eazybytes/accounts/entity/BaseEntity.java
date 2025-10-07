package com.eazybytes.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@MappedSuperclass //This Annotation is used to define a class as a parent/super class for all the entity classes.
@Getter @Setter @ToString
public class BaseEntity { // This is the parent class for all the entity classes.It is not a table in the database.It is used to store common fields for all the entity classes.

    @Column(name = "created_at",updatable = false) // means don't include this column in the update query
    private LocalDateTime createdAt;

    @Column(name = "created_by",updatable = false)
    private String createdBy;

    @Column(name = "updated_at",insertable = false) // means don't include this column in the insert query
    private LocalDateTime updatedAt;

    @Column(name = "updated_by",insertable = false)
    private String updatedBy;
}
