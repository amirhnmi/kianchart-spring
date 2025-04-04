package com.kianchart.kianchart.database.repository.permissions;

import com.kianchart.kianchart.database.entity.permissions.Permission;
import com.kianchart.kianchart.database.entity.permissions.Role;
import com.kianchart.kianchart.database.entity.permissions.RolePermission;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission,Long> {
    @Query("SELECT rp FROM RolePermission rp ORDER BY rp.id ASC")
    List<RolePermission> getAllRolePermissionASC();

    @Query("SELECT rp FROM RolePermission rp ORDER BY rp.id DESC")
    List<RolePermission> getAllRolePermissionDESC();

    @Query("SELECT rp.permission.id FROM RolePermission rp WHERE rp.role.id=:role_id ORDER BY rp.id ASC")
    Set<Long> getAllPermissionInRolePermission(@Param("role_id") Long roleId);

    @Query("SELECT r FROM Role r WHERE r.id=:id AND r.isDelete=false")
    Role getOneRole(@Param("id") Long id);

    @Query("SELECT p FROM Permission p WHERE p.id=:id AND p.isDelete=false")
    Permission getOnePermission(@Param("id") Long id);

    @Query("SELECT COUNT(p) > 0 FROM Permission p WHERE p.id=:permission_id AND p.isDelete=false")
    boolean existsByPermissionId(@Param("permission_id") Long PermissionId);

    @Query("SELECT COUNT(r) > 0 FROM Role r WHERE r.id=:role_id AND r.isDelete=false")
    boolean existsByRoleId(@Param("role_id") Long roleId);

    @Query("SELECT COUNT(rp) > 0 FROM RolePermission rp WHERE rp.permission.id=:permission_id AND rp.role.id=:role_id")
    boolean existsByPermissionIdRoleId(@Param("permission_id") Long PermissionId, @Param("role_id") Long roleId);

    boolean existsById(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM RolePermission rp WHERE rp.role.id=:role_id AND rp.permission.id IN :permission_ids")
    void deleteByRoleIdAndPermissionIds(@Param("role_id") Long roleId, @Param("permission_ids") Set<Long> PermissionIds);
}
