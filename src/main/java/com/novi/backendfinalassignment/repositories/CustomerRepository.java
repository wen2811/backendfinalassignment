package com.novi.backendfinalassignment.repositories;

import com.novi.backendfinalassignment.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
