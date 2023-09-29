package com.novi.backendfinalassignment.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @OneToOne(mappedBy = "booking")
    //@JsonIgnore
    private Invoice invoice;

    @OneToMany(mappedBy = "booking")
    private List<BookingTreatment> bookingTreatments;


    //JsonIgnore
     @ManyToOne
    //@JoinColumn(name = "customerId")
    private Customer customer;

  //JsonIgnore
    @ManyToOne
   // @JoinColumn(name = "user_id")
    private User user;

    public Booking(LocalDate date, double totalAmount ){
        this.date = date;
        this.totalAmount = totalAmount;
    }



   /* public double calculateTotalAmount() {
        double totalAmount = 0.0;
        for (Treatment treatmentItem : treatment) {
            totalAmount += treatmentItem.getPrice() * treatmentItem.getQuantity();
        }
        return totalAmount;
    }*/

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<BookingTreatment> getBookingTreatments() {
        return bookingTreatments;
    }

    public void setBookingTreatments(List<BookingTreatment> bookingTreatments) {
        this.bookingTreatments = bookingTreatments;
    }
}
