package com.novi.backendfinalassignment.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "treatments")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Treatment name is required")
    private String name;
    @Enumerated(EnumType.STRING)
    private TreatmentType type;
    @Column(columnDefinition = "TEXT")
    private String description;
    private double duration;
    private double price;
    private int quantity;
    //private double sum;


    @OneToOne //(mappedBy = "treatment")
    private Calendar calendar;

    @ManyToOne
    //@JoinColumn(name = "bookingId")
    //@JoinColumn(name = "treatment_id")
    private Booking booking;


    public Treatment(String name, TreatmentType type, String description, double duration, double price, int quantity) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.quantity = quantity;
        //this.sum = sum;

    }

   /* public double calculateSum(){
        return this.quantity * this.price;
    }*/

}

