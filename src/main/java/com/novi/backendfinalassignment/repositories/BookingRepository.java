package com.novi.backendfinalassignment.repositories;

import com.novi.backendfinalassignment.models.Booking;
import com.novi.backendfinalassignment.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomer(Customer customer);
}
