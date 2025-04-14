package com.kianchart.kianchart.repository;

import com.kianchart.kianchart.enums.FileType;
import com.kianchart.kianchart.entity.FileEntity;
import com.kianchart.kianchart.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.id=:id AND u.isActive=true AND u.isDelete=false")
    UserEntity findOneUser(@Param("id") Long id);

    @Query("SELECT f FROM FileEntity f WHERE f.isDelete=false AND f.type=:type ORDER BY f.id ASC")
    List<FileEntity> getAllFileASC(@Param("type") FileType fileType);

    @Query("SELECT f FROM FileEntity f WHERE f.isDelete=false AND f.type=:type ORDER BY f.id DESC")
    List<FileEntity> getAllImageDESC(@Param("type") FileType fileType);

    @Query("SELECT COUNT(f) > 0 FROM FileEntity f WHERE f.id=:id AND f.isDelete=false ")
    boolean existingById(@Param("id") Long id);

    @Query("SELECT COUNT(f) FROM FileEntity f WHERE f.isDelete=false AND f.type=:type")
    Long countAllFile(@Param("type") FileType fileType);

    @Transactional
    @Modifying
    @Query("UPDATE FileEntity f SET f.isDelete=true, f.deletedAt=CURRENT TIMESTAMP WHERE f.id=:id")
    void deleteFile(@Param("id") Long id);
}
