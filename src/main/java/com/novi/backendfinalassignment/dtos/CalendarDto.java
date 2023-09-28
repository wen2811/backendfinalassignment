package com.novi.backendfinalassignment.dtos;

import com.novi.backendfinalassignment.models.Treatment;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class CalendarDto {
    public Long id;
    @FutureOrPresent
    public LocalDate date;
    public LocalTime startTime;
    public LocalTime endTime;
    public boolean availableTime;
    public Treatment treatment;


}
