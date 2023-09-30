package com.novi.backendfinalassignment.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bookingtreatments")
public class BookingTreatment {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private String customerName;
    private String customerEmail;


    @ManyToOne
    //@JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
   // @JoinColumn(name = "treatment_id")
    private Treatment treatment;
}
