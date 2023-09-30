package com.novi.backendfinalassignment.dtos;

import com.novi.backendfinalassignment.models.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookingDto {
    public Long id;
    public LocalDate date;
    public double totalAmount;
    public User employee;
    public BookingStatus bookingStatus;


    public List<BookingTreatment> bookingTreatments;
    public Customer customer;
    public Invoice invoice;


    public Long getCustomerId() {
        return id;
        }


}
