package com.novi.backendfinalassignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.novi.backendfinalassignment.models.Calendar;


public interface CalendarRepository extends JpaRepository<Calendar, Long> {

}
