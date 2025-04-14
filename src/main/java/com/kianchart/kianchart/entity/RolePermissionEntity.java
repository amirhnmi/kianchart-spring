package com.kianchart.kianchart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "role_permissions")
    public class RolePermissionEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "role_id", nullable = false)
        private RoleEntity role;

        @ManyToOne
        @JoinColumn(name = "permission_id", nullable = false)
        private PermissionEntity permission;

        @Column(name = "created_at", nullable = false, updatable = false)
        @CreationTimestamp
        private LocalDateTime createdAt;

    }
