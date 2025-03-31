package com.kianchart.kianchart.database.repository.permissions;

import com.kianchart.kianchart.database.entity.permissions.Permission;
import com.kianchart.kianchart.database.entity.permissions.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {
    @Query("SELECT p FROM Permission p WHERE p.isDelete=false")
    List<Permission> getAllPermission();

    @Query("SELECT p FROM Permission p WHERE p.id=:id AND p.isDelete=false")
    Permission getOnePermission(@Param("id") Long id);

    @Query("SELECT COUNT(p) > 0 FROM Permission p WHERE p.id=:id AND p.isDelete=false")
    boolean existingById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Permission p SET p.isDelete=true, p.deletedAt=CURRENT TIMESTAMP WHERE p.id=:id")
    void deleteRole(@Param("id") Long id);

    Boolean existsBySlug(String slug);
}
