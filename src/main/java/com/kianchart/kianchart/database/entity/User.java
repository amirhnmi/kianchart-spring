package com.kianchart.kianchart.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 128, nullable = false)
    private String username;

    @Column(length = 128, nullable = false)
    private String fullname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String gender;

    private UUID uuid;

    @Column(name = "is_active",nullable = false)
    private Boolean isActive;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}

