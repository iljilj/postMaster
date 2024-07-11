package com.example.hop.repository;

import com.example.hop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

    List<User> findByIsBannedTrue();

    List<User> findByRoles_Name(String roleName);
}