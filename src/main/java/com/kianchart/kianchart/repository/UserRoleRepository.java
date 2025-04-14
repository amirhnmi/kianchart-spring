package com.kianchart.kianchart.repository;

import com.kianchart.kianchart.entity.RoleEntity;
import com.kianchart.kianchart.entity.UserEntity;
import com.kianchart.kianchart.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity,Long> {
    @Query("SELECT ur FROM UserRoleEntity ur ORDER BY ur.id ASC ")
    List<UserRoleEntity> getAllUserRoleASC();

    @Query("SELECT ur FROM UserRoleEntity ur ORDER BY ur.id DESC ")
    List<UserRoleEntity> getAllUserRoleDESC();

    @Query("SELECT r FROM RoleEntity r WHERE r.id=:id AND r.isDelete=false")
    RoleEntity getOneRole(@Param("id") Long id);

    @Query("SELECT u FROM UserEntity u WHERE u.id=:id AND u.isDelete=false AND u.isActive=true ")
    UserEntity getOneUser(@Param("id") Long id);

    @Query("SELECT COUNT(r) > 0 FROM RoleEntity r WHERE r.id=:role_id AND r.isDelete=false")
    boolean existsByRoleId(@Param("role_id") Long roleId);

    @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE u.id=:user_id AND u.isDelete=false AND u.isActive=true ")
    boolean existsByUserId(@Param("user_id") Long userId);

    @Query("SELECT COUNT(ur) > 0 FROM UserRoleEntity ur WHERE ur.user.id=:user_id AND ur.role.id=:role_id")
    boolean existsByUserIdRoleId(@Param("user_id") Long userId, @Param("role_id") Long roleId);

    @Query("SELECT COUNT(ur) > 0 FROM UserRoleEntity ur WHERE ur.id=:id")
    boolean existingById(@Param("id") Long id);

}
