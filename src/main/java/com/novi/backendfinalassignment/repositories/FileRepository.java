package com.novi.backendfinalassignment.repositories;

import com.novi.backendfinalassignment.models.Customer;
import com.novi.backendfinalassignment.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    File findByFilename(String fileName);

    List<File> findByCustomer(Customer customer);
}
