package com.novi.backendfinalassignment.dtos;

import com.novi.backendfinalassignment.models.Booking;
import com.novi.backendfinalassignment.models.Treatment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingTreatmentDto {
    public Long id;
    public int quantity;
    public String customerName;
    public String customerEmail;
    public Booking booking;
    public Treatment treatment;
}
