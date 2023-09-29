package com.novi.backendfinalassignment.controllers;

import com.novi.backendfinalassignment.dtos.BookingDto;
import com.novi.backendfinalassignment.dtos.BookingTreatmentDto;
import com.novi.backendfinalassignment.exceptions.RecordNotFoundException;
import com.novi.backendfinalassignment.services.BookingTreatmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bookingtreatments")
public class BookingTreatmentController {
    private final BookingTreatmentService bookingTreatmentService;


    public BookingTreatmentController(BookingTreatmentService bookingTreatmentService) {
        this.bookingTreatmentService = bookingTreatmentService;

    }
    
    //Read
    @GetMapping
    public ResponseEntity<List<BookingTreatmentDto>>getAllBookingTreatment() {
        return ResponseEntity.ok().body(bookingTreatmentService.getAllBookingTreatment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingTreatmentDto> getBookingTreatment(@PathVariable Long id) {
        return ResponseEntity.ok().body(bookingTreatmentService.getBookingTreatment(id));
    }

    //Create
    @PostMapping
    public ResponseEntity<Object> addBookingTreatment(@Valid @RequestBody BookingTreatmentDto bookingTreatmentDto, BindingResult br) {
        if(br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            BookingTreatmentDto addedBookingTreatment = bookingTreatmentService.addBookingTreatment(bookingTreatmentDto);
            URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + addedBookingTreatment.id)));
            return ResponseEntity.created(uri).body(addedBookingTreatment);
        }
    }
    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBookingTreatment(@PathVariable Long id, @RequestBody BookingTreatmentDto bookingTreatmentDto) throws RecordNotFoundException {
        BookingTreatmentDto updateBookingTreatment = bookingTreatmentService.updateBookingTreatment(id, bookingTreatmentDto);
        return ResponseEntity.ok().body(updateBookingTreatment);
    }
    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBookingTreatment(@PathVariable Long id) throws RecordNotFoundException {
        bookingTreatmentService.deleteBookingTreatment(id);
        return ResponseEntity.noContent().build();
    }
}
