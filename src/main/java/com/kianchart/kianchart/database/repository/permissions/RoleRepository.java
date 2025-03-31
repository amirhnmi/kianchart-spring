package com.kianchart.kianchart.database.repository.permissions;

import com.kianchart.kianchart.database.entity.permissions.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    @Query("SELECT r FROM Role r WHERE r.isDelete=false ORDER BY r.id ASC")
    List<Role> getAllRoleASC();

    @Query("SELECT r FROM Role r WHERE r.isDelete=false ORDER BY r.id desc")
    List<Role> getAllRoleDESC();

    @Query("SELECT COUNT(r) FROM Role r WHERE r.isDelete=false")
    Long countAllRole();

    @Query("SELECT r FROM Role r WHERE r.id=:id AND r.isDelete=false")
    Role getOneRole(@Param("id") Long id);

    @Query("SELECT COUNT(r) > 0 FROM Role r WHERE r.id=:id AND r.isDelete=false")
    boolean existingById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Role r SET r.isDelete=true, r.deletedAt=CURRENT TIMESTAMP WHERE r.id=:id")
    void deleteRole(@Param("id") Long id);

    Boolean existsBySlug(String slug);
}
