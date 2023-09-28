package com.novi.backendfinalassignment.repositories;

import com.novi.backendfinalassignment.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {
}
