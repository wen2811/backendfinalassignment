package com.novi.backendfinalassignment.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


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


    @OneToOne //(mappedBy = "treatment")
    private Calendar calendar;

    @OneToMany
    //@JoinColumn(name = "bookingId")
    //@JoinColumn(name = "treatment_id")
    private List<BookingTreatment> bookingTreatments;


    public Treatment(String name, TreatmentType type, String description, double duration, double price) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.duration = duration;
        this.price = price;


    }

   /* public double calculateSum(){
        return this.quantity * this.price;
    }*/

}

