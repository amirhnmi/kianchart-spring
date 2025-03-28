package com.kianchart.kianchart.database.repository;

import com.kianchart.kianchart.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);
}

