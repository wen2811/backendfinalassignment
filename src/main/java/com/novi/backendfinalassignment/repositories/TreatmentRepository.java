package com.novi.backendfinalassignment.repositories;

import com.novi.backendfinalassignment.models.Treatment;
import com.novi.backendfinalassignment.models.TreatmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TreatmentRepository extends JpaRepository<Treatment, Long>{
    Treatment findById(Treatment treatment);

    Optional<Treatment> findTreatmentsByType(TreatmentType type);
}
