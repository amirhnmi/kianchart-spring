package com.kianchart.kianchart.repository;

import com.kianchart.kianchart.entity.PermissionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity,Long> {
    @Query("SELECT p FROM PermissionEntity p WHERE p.isDelete=false ORDER BY p.id ASC ")
    List<PermissionEntity> getAllPermissionASC();

    @Query("SELECT p FROM PermissionEntity p WHERE p.isDelete=false ORDER BY p.id DESC ")
    List<PermissionEntity> getAllPermissionDESC();

    @Query("SELECT COUNT(p) FROM PermissionEntity p WHERE p.isDelete=false")
    Long countAllPermissions();

    @Query("SELECT p FROM PermissionEntity p WHERE p.id=:id AND p.isDelete=false")
    PermissionEntity getOnePermission(@Param("id") Long id);

    @Query("SELECT COUNT(p) > 0 FROM PermissionEntity p WHERE p.id=:id AND p.isDelete=false")
    boolean existingById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE PermissionEntity p SET p.isDelete=true, p.deletedAt=CURRENT TIMESTAMP WHERE p.id=:id")
    void deletePermission(@Param("id") Long id);

    @Query("SELECT COUNT(p) > 0 FROM PermissionEntity p WHERE p.id=:id AND p.isDelete=false")
    boolean existsById(@Param("id") Long id);

    Boolean existsBySlug(String slug);

}
