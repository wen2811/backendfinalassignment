package com.novi.backendfinalassignment.controllers;

import com.novi.backendfinalassignment.dtos.BookingDto;
import com.novi.backendfinalassignment.dtos.BookingTreatmentDto;
import com.novi.backendfinalassignment.dtos.CalendarDto;
import com.novi.backendfinalassignment.dtos.TreatmentDto;
import com.novi.backendfinalassignment.exceptions.RecordNotFoundException;
import com.novi.backendfinalassignment.models.Calendar;
import com.novi.backendfinalassignment.models.Treatment;
import com.novi.backendfinalassignment.models.TreatmentType;
import com.novi.backendfinalassignment.services.TreatmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/treatments")
public class TreatmentController {
    private final TreatmentService treatmentService;

    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    //Read
    @GetMapping("/treatments")
    public ResponseEntity<List<TreatmentDto>> getAllTreatments() {
        return ResponseEntity.ok().body(treatmentService.getAllTreatments());
    }

    @GetMapping("/{type}")
    public ResponseEntity<TreatmentDto> getTreatmentsByType(@PathVariable TreatmentType type) throws RecordNotFoundException {
        return ResponseEntity.ok().body(treatmentService.getTreatmentsByType(type));
    }
    
    //Create
    @PostMapping("/treatments")
    public ResponseEntity<Object> addTreatment(@Valid @RequestBody TreatmentDto treatmentDto, BindingResult br) {
        if(br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            TreatmentDto addedTreatment = treatmentService.addTreatment(treatmentDto);
            URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + addedTreatment.id)));
            return ResponseEntity.created(uri).body(addedTreatment);
        }
    }

    //Update
    @PutMapping("/treatments/{id}")
    public ResponseEntity<Object> updateTreatment(@PathVariable("id") Long id, @RequestBody TreatmentDto treatmentDto) {
        treatmentService.updateTreatment(id, treatmentDto);
        return ResponseEntity.ok(treatmentDto);
    }


    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTreatment(@PathVariable Long id) throws RecordNotFoundException  {
        treatmentService.deleteTreatment(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTreatmentWithCalendar(@PathVariable Long id, @RequestBody TreatmentDto treatmentDto, @RequestBody CalendarDto calendarDto) throws RecordNotFoundException {
        treatmentService.updateTreatmentWithCalendar(id, treatmentDto, calendarDto);
        return ResponseEntity.ok("Behandeling met kalendergegevens is succesvol bijgewerkt.");
    }

    @GetMapping("/{id}/with-calendar")
    public ResponseEntity<TreatmentDto> getTreatmentWithCalendar(@PathVariable Long id) throws RecordNotFoundException {
        TreatmentDto treatmentDto = treatmentService.getTreatmentWithCalendar(id);
        return ResponseEntity.ok(treatmentDto);
    }

    @PostMapping("/{treatmentId}/bookingtreatments")
    public ResponseEntity<BookingTreatmentDto> addBookingTreatmentToTreatment(@PathVariable Long treatmentId, @RequestBody BookingTreatmentDto bookingTreatmentDto) {
        BookingTreatmentDto addedBookingTreatment = treatmentService.addBookingTreatmentToTreatment(treatmentId, bookingTreatmentDto);
        return new ResponseEntity<>(addedBookingTreatment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/updateBookingTreatment")
    public ResponseEntity<BookingTreatmentDto> updateBookingTreatment(@PathVariable Long id, @RequestBody BookingTreatmentDto bookingTreatmentDto) throws RecordNotFoundException {
        BookingTreatmentDto updatedBookingTreatment = treatmentService.updateBookingTreatment(id, bookingTreatmentDto);
        return ResponseEntity.ok(updatedBookingTreatment);
    }

    @GetMapping("/bookingtreatment/{id}")
    public ResponseEntity<BookingTreatmentDto> getBookingTreatmentById(@PathVariable Long id) throws RecordNotFoundException {
        BookingTreatmentDto bookingTreatment = treatmentService.getBookingTreatmentById(id);
        return ResponseEntity.ok(bookingTreatment);
    }

}









    /*@GetMapping("/{id}/amount")
    public ResponseEntity<TreatmentDto> getSum(@PathVariable Long id) throws RecordNotFoundException {
        TreatmentDto treatmentDto = new TreatmentDto();
        treatmentDto.id = id;
        treatmentDto.sum = treatmentService.getSum(id);
        return new ResponseEntity<>(treatmentDto, HttpStatus.OK);
    }
*/


