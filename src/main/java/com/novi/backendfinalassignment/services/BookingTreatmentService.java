package com.novi.backendfinalassignment.services;

import com.novi.backendfinalassignment.dtos.BookingTreatmentDto;
import com.novi.backendfinalassignment.dtos.BookingTreatmentDto;
import com.novi.backendfinalassignment.models.Booking;
import com.novi.backendfinalassignment.models.BookingTreatment;
import com.novi.backendfinalassignment.repositories.BookingTreatmentRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingTreatmentService {
    private final BookingTreatmentRepository bookingTreatmentRepository;

    public BookingTreatmentService(BookingTreatmentRepository bookingTreatmentRepository) {
        this.bookingTreatmentRepository = bookingTreatmentRepository;
    }
    //Read
    //Create
     public BookingTreatmentDto addBookingTreatment(BookingTreatmentDto bookingTreatmentDto) {
        BookingTreatment bookingTreatment = transferDtoToBookingTreatment(bookingTreatmentDto);
        bookingTreatmentRepository.save(bookingTreatment);

        return transferBookingTreatmentToDto(bookingTreatment);
    }
    //Update
    //Delete

    public BookingTreatmentDto transferBookingTreatmentToDto(BookingTreatment bookingTreatment) {
        BookingTreatmentDto bookingTreatmentDto = new BookingTreatmentDto();

        bookingTreatmentDto.id = bookingTreatment.getId();
        bookingTreatmentDto.quantity = bookingTreatment.getQuantity();
        bookingTreatmentDto.treatment = bookingTreatment.getTreatment();
        bookingTreatmentDto.booking = bookingTreatment.getBooking();
        return bookingTreatmentDto;
    }
    public BookingTreatment transferDtoToBookingTreatment(BookingTreatmentDto bookingTreatmentDto) {
        BookingTreatment bookingTreatment = new BookingTreatment();

        bookingTreatmentDto.setId(bookingTreatmentDto.id);
        bookingTreatmentDto.setQuantity(bookingTreatmentDto.quantity);
        bookingTreatmentDto.setTreatment(bookingTreatmentDto.treatment);
        bookingTreatmentDto.setBooking(bookingTreatmentDto.booking);
        return bookingTreatment;
    }
}
