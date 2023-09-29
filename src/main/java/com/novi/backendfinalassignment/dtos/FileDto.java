package com.novi.backendfinalassignment.dtos;


import com.novi.backendfinalassignment.models.Customer;
import com.novi.backendfinalassignment.models.User;
import com.novi.backendfinalassignment.models.UserRole;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FileDto {
    public Long id;
    public String filename;
    public String filetype;
    public LocalDate date;
    public byte[] data;
    public String mimeType;
    public String description;
    public Customer customer;

}
