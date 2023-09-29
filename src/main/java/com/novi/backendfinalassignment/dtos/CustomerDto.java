package com.novi.backendfinalassignment.dtos;

import com.novi.backendfinalassignment.models.Booking;
import com.novi.backendfinalassignment.models.File;
import com.novi.backendfinalassignment.models.Invoice;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerDto {
    public Long id;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String email;
    public List<Booking> bookingList;
    public List<Invoice> invoice;
    public List<File> fileList;
}
