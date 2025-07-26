package com.example.trail.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.trail.Tables.User;



@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // Spring Data JPA will automatically implement this based on method name
    Optional<User> findByEmail(String email);
}