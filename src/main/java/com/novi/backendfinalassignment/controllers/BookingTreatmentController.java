package com.novi.backendfinalassignment.controllers;

import com.novi.backendfinalassignment.services.BookingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookingtreatments")
public class BookingTreatmentController {
    private final BookingService bookingService;


    public BookingTreatmentController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

}
