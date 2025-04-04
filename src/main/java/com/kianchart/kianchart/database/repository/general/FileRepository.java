package com.kianchart.kianchart.database.repository.general;

import com.kianchart.kianchart.core.enums.FileType;
import com.kianchart.kianchart.database.entity.general.File;
import com.kianchart.kianchart.database.entity.users.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    @Query("SELECT u FROM User u WHERE u.id=:id AND u.isActive=true AND u.isDelete=false")
    User findOneUser(@Param("id") Long id);

    @Query("SELECT f FROM File f WHERE f.isDelete=false AND f.type=:type ORDER BY f.id ASC")
    List<File> getAllFileASC(@Param("type") FileType fileType);

    @Query("SELECT f FROM File f WHERE f.isDelete=false AND f.type=:type ORDER BY f.id DESC")
    List<File> getAllImageDESC(@Param("type") FileType fileType);

    @Query("SELECT COUNT(f) > 0 FROM File f WHERE f.id=:id AND f.isDelete=false ")
    boolean existingById(@Param("id") Long id);

    @Query("SELECT COUNT(f) FROM File f WHERE f.isDelete=false AND f.type=:type")
    Long countAllFile(@Param("type") FileType fileType);

    @Transactional
    @Modifying
    @Query("UPDATE File f SET f.isDelete=true, f.deletedAt=CURRENT TIMESTAMP WHERE f.id=:id")
    void deleteFile(@Param("id") Long id);
}
