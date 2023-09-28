package com.novi.backendfinalassignment.controllers;

import com.novi.backendfinalassignment.dtos.BookingDto;
import com.novi.backendfinalassignment.dtos.InvoiceDto;
import com.novi.backendfinalassignment.exceptions.RecordNotFoundException;
import com.novi.backendfinalassignment.models.Booking;
import com.novi.backendfinalassignment.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;


    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;

    }

    //Read
    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBooking() {
        return ResponseEntity.ok().body(bookingService.getAllBooking());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok().body(bookingService.getBooking(id));
    }

    //Create
   /* @PostMapping
    public ResponseEntity<Object> addBooking(@Valid @RequestBody BookingDto bookingDto, BindingResult br) {
        if(br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            BookingDto addedBooking = bookingService.addBooking(bookingDto);
            URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + addedBooking.id)));
            return ResponseEntity.created(uri).body(addedBooking);
        }
    }*/

    @PostMapping("/")
    public ResponseEntity<Object> createBooking(@RequestParam Long customerId, @RequestParam List<Long> treatmentId) {
        Booking booking = bookingService.createBooking(customerId, treatmentId);
        if (booking == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }


    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBooking(@PathVariable Long id, @RequestBody BookingDto bookingDto) throws RecordNotFoundException {
        BookingDto updateBooking = bookingService.updateBooking(id, bookingDto);
        return ResponseEntity.ok().body(updateBooking);
    }


    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBooking(@PathVariable Long id) throws RecordNotFoundException {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/totalAmount")
    public ResponseEntity<BookingDto> getTotalAmount(@PathVariable Long id) throws RecordNotFoundException {
        BookingDto bookingDto = new BookingDto();
        bookingDto.id = id;
        bookingDto.totalAmount = bookingService.getTotalAmount(id);
        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }

    @GetMapping("/{customerId}/bookings")
    public ResponseEntity<List<BookingDto>> getBookingsForCustomer(@PathVariable Long customerId) throws RecordNotFoundException {
        List<BookingDto> bookingDtos = bookingService.getBookingsForCustomer(customerId);
        return ResponseEntity.ok().body(bookingDtos);
    }

}





    // ResponseEntity cancelAppointment(Long appointmentId);

    //ResponseEntity findAllByAppointmentDateAfter(LocalDate startDate, String status);

    //ResponseEntity findAllByAppointmentDateBefore(LocalDate startDate, String status);

    //ResponseEntity findByUserAndDateRangeWithStatus(LocalDate startDate, LocalDate endDate, Long userId, String status);

    //ResponseEntity findAllByUserAndAppointmentDateAfter(LocalDate date, Long userId, String status);

    //ResponseEntity findAllByUserAndAppointmentDateBefore(LocalDate date, Long userId, String status);

    //ResponseEntity createBatchAppointment(BatchAppointment batchAppointment);

