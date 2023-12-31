package com.novi.backendfinalassignment.dtos;

import com.novi.backendfinalassignment.models.BookingTreatment;
import com.novi.backendfinalassignment.models.Calendar;
import com.novi.backendfinalassignment.models.TreatmentType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TreatmentDto {

 public Long id;

 @NotBlank(message = "Treatment name is required")
 public String name;
 public TreatmentType type;
 public String description;
 public double duration;
public double price;
public Calendar calendar;
 public List<BookingTreatment> bookingTreatments;


 public double getPrice() {
  return price;
 }

 public void setPrice(double price) {
  this.price = price;
 }
}
