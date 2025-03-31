package com.kianchart.kianchart.database.repository.users;

import com.kianchart.kianchart.database.entity.users.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    @Query("SELECT ur FROM UserRole ur WHERE ur.isDelete=false")
    List<UserRole> getAllUserRole();

    @Query("SELECT ur FROM UserRole ur WHERE ur.id=:id AND ur.isDelete=false")
    UserRole getOneUserRole(@Param("id") Long id);

    @Query("SELECT COUNT(ur) > 0 FROM UserRole ur WHERE ur.id=:id AND ur.isDelete=false")
    boolean existingById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE UserRole ur SET ur.isDelete=true, ur.deletedAt=CURRENT TIMESTAMP WHERE ur.id=:id")
    void deleteUserRole(@Param("id") Long id);
}
