package com.kianchart.kianchart.database.entity.general;

import com.kianchart.kianchart.core.enums.FileType;
import com.kianchart.kianchart.database.entity.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id",nullable = false)
    private User creator;

    @Column(length = 256,nullable = false)
    private String file;

    @Column(nullable = false)
    private Long size;

    @Column(length = 16,nullable = false)
    @Enumerated(EnumType.STRING)
    private FileType type;

    @Column(length = 32,nullable = false)
    private String extension;

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
