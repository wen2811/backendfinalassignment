package com.novi.backendfinalassignment.repositories;

import com.novi.backendfinalassignment.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
    File findByFilename(String fileName);
}
