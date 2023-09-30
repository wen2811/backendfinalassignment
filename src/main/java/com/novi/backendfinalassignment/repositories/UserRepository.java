package com.novi.backendfinalassignment.repositories;

import com.novi.backendfinalassignment.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {
    Optional<Object> findById(Long userId);

}
