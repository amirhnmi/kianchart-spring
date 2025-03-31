package com.kianchart.kianchart.database.repository.users;

import com.kianchart.kianchart.database.entity.users.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.isActive=true AND u.isDelete=false ORDER BY u.id asc")
    List<User> findAllUsersASC();

    @Query("SELECT u FROM User u WHERE u.isActive=true AND u.isDelete=false ORDER BY u.id desc")
    List<User> findAllUsersDESC();

    @Query("SELECT COUNT(u) FROM User u WHERE u.isDelete=false AND u.isActive=TRUE")
    Long countAllActiveUser();

    @Query("SELECT u FROM User u WHERE u.id=:id AND u.isActive=true AND u.isDelete=false")
    User findOneUser(@Param("id") Long id);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.id=:id AND u.isDelete=false AND u.isActive=true")
    boolean existsById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isDelete=true, u.deletedAt=CURRENT TIMESTAMP WHERE u.id=:id")
    void deleteUser(@Param("id") Long id);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);
}

