package com.novi.backendfinalassignment.controllers;

import com.novi.backendfinalassignment.dtos.BookingDto;
import com.novi.backendfinalassignment.dtos.CustomerDto;
import com.novi.backendfinalassignment.exceptions.RecordNotFoundException;
import com.novi.backendfinalassignment.models.Booking;
import com.novi.backendfinalassignment.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/")
    public ResponseEntity<Object> createBooking(@RequestParam Long customerId, @RequestParam List<Long> bookingTreatmentIds, @RequestBody CustomerDto customerDto) {
        Booking booking = bookingService.createBooking(customerId, bookingTreatmentIds, customerDto);
        if (booking == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }


    //Update 1 algemeen
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBooking(@PathVariable Long id, @RequestBody BookingDto bookingDto) throws RecordNotFoundException {
        BookingDto updateBooking = bookingService.updateBooking(id, bookingDto);
        return ResponseEntity.ok().body(updateBooking);
    }

    // Update 2
    @PutMapping("/updateTreatments/{id}")
    public ResponseEntity<BookingDto> updateBookingTreatments(@PathVariable Long id, @RequestBody List<Long> treatmentIds) throws RecordNotFoundException {
        BookingDto updatedBookingDto = bookingService.updateBookingTreatments(id,treatmentIds );
        return new ResponseEntity<>(updatedBookingDto, HttpStatus.OK);
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBooking(@PathVariable Long id) throws RecordNotFoundException {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    /*@GetMapping("/{id}/totalAmount")
    public ResponseEntity<BookingDto> getTotalAmount(@PathVariable Long id) throws RecordNotFoundException {
        BookingDto bookingDto = new BookingDto();
        bookingDto.id = id;
        bookingDto.totalAmount = bookingService.getTotalAmount(id);
        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }*/

    @GetMapping("/{customerId}/bookings")
    public ResponseEntity<List<BookingDto>> getBookingsForCustomer(@PathVariable Long customerId) throws RecordNotFoundException {
        List<BookingDto> bookingDtos = bookingService.getBookingsForCustomer(customerId);
        return ResponseEntity.ok().body(bookingDtos);
    }


    @PostMapping("/createWithoutRegistration")
    public ResponseEntity<BookingDto> createBookingWithoutRegistration(@RequestParam Long customerId, @RequestParam List<Long> bookingTreatmentIds, @RequestBody(required = false) CustomerDto customerDto) {
        BookingDto createdBooking = bookingService.createBookingWithoutRegistration(customerId, bookingTreatmentIds, customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }

}





    // ResponseEntity cancelAppointment(Long appointmentId);

    //ResponseEntity findAllByAppointmentDateAfter(LocalDate startDate, String status);

    //ResponseEntity findAllByAppointmentDateBefore(LocalDate startDate, String status);

    //ResponseEntity findByUserAndDateRangeWithStatus(LocalDate startDate, LocalDate endDate, Long userId, String status);

    //ResponseEntity findAllByUserAndAppointmentDateAfter(LocalDate date, Long userId, String status);

    //ResponseEntity findAllByUserAndAppointmentDateBefore(LocalDate date, Long userId, String status);

    //ResponseEntity createBatchAppointment(BatchAppointment batchAppointment);

