package com.novi.backendfinalassignment.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    @OneToMany(mappedBy = "customer")
   // @JoinColumn(name = "customerId")
    @JsonIgnore
    private List<Booking> bookingList;

    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoice;
    @OneToMany(mappedBy = "customer")
    private List<File> fileList;

    public List<Invoice> getInvoice() {
        return invoice;
    }

    public void setInvoice(List<Invoice> invoice) {
        this.invoice = invoice;
    }
}
