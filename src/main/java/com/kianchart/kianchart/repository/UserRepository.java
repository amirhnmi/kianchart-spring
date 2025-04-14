package com.kianchart.kianchart.repository;

import com.kianchart.kianchart.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.isActive=true AND u.isDelete=false ORDER BY u.id asc")
    List<UserEntity> findAllUsersASC();

    @Query("SELECT u FROM UserEntity u WHERE u.isActive=true AND u.isDelete=false ORDER BY u.id desc")
    List<UserEntity> findAllUsersDESC();

    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.isDelete=false AND u.isActive=TRUE")
    Long countAllActiveUser();

    @Query("SELECT u FROM UserEntity u WHERE u.id=:id AND u.isActive=true AND u.isDelete=false")
    UserEntity findOneUser(@Param("id") Long id);

    @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE u.id=:id AND u.isDelete=false AND u.isActive=true")
    boolean existsById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.isDelete=true, u.deletedAt=CURRENT TIMESTAMP WHERE u.id=:id")
    void deleteUser(@Param("id") Long id);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);
}

