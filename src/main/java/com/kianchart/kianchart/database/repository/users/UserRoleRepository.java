package com.kianchart.kianchart.database.repository.users;

import com.kianchart.kianchart.database.entity.permissions.Role;
import com.kianchart.kianchart.database.entity.users.User;
import com.kianchart.kianchart.database.entity.users.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    @Query("SELECT ur FROM UserRole ur ORDER BY ur.id ASC ")
    List<UserRole> getAllUserRoleASC();

    @Query("SELECT ur FROM UserRole ur ORDER BY ur.id DESC ")
    List<UserRole> getAllUserRoleDESC();

    @Query("SELECT r FROM Role r WHERE r.id=:id AND r.isDelete=false")
    Role getOneRole(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.id=:id AND u.isDelete=false AND u.isActive=true ")
    User getOneUser(@Param("id") Long id);

    @Query("SELECT COUNT(r) > 0 FROM Role r WHERE r.id=:role_id AND r.isDelete=false")
    boolean existsByRoleId(@Param("role_id") Long roleId);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.id=:user_id AND u.isDelete=false AND u.isActive=true ")
    boolean existsByUserId(@Param("user_id") Long userId);

    @Query("SELECT COUNT(ur) > 0 FROM UserRole ur WHERE ur.user.id=:user_id AND ur.role.id=:role_id")
    boolean existsByUserIdRoleId(@Param("user_id") Long userId, @Param("role_id") Long roleId);

    @Query("SELECT COUNT(ur) > 0 FROM UserRole ur WHERE ur.id=:id")
    boolean existingById(@Param("id") Long id);

}
