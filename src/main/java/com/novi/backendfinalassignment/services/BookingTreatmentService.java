package com.novi.backendfinalassignment.services;

import com.novi.backendfinalassignment.dtos.BookingTreatmentDto;
import com.novi.backendfinalassignment.exceptions.RecordNotFoundException;
import com.novi.backendfinalassignment.models.BookingTreatment;
import com.novi.backendfinalassignment.models.Treatment;
import com.novi.backendfinalassignment.repositories.BookingTreatmentRepository;
import com.novi.backendfinalassignment.repositories.TreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingTreatmentService {
    private final BookingTreatmentRepository bookingTreatmentRepository;
    private final TreatmentRepository treatmentRepository;

    public BookingTreatmentService(BookingTreatmentRepository bookingTreatmentRepository, TreatmentRepository treatmentRepository) {
        this.bookingTreatmentRepository = bookingTreatmentRepository;
        this.treatmentRepository = treatmentRepository;
    }
    //Read
    public List<BookingTreatmentDto>getAllBookingTreatment(){
        Iterable<BookingTreatment> bookingTreatments = bookingTreatmentRepository.findAll();
        List<BookingTreatmentDto> bookingTreatmentDtos = new ArrayList<>();

        for (BookingTreatment bt: bookingTreatments){
            bookingTreatmentDtos.add(transferBookingTreatmentToDto(bt));
        }
        return bookingTreatmentDtos;
    }

    public BookingTreatmentDto getBookingTreatment(Long id) {
        Optional<BookingTreatment> bookingTreatmentOptional = bookingTreatmentRepository.findById(id);

        if (bookingTreatmentOptional.isEmpty()){
            throw new RecordNotFoundException("There is no bookingtreatment found with id: " + id);
        }
        BookingTreatment bookingTreatment = bookingTreatmentOptional.get();
        return transferBookingTreatmentToDto(bookingTreatment);
    }


    //Create
     public BookingTreatmentDto addBookingTreatment(BookingTreatmentDto bookingTreatmentDto) {
        BookingTreatment bookingTreatment = transferDtoToBookingTreatment(bookingTreatmentDto);
        bookingTreatmentRepository.save(bookingTreatment);

        return transferBookingTreatmentToDto(bookingTreatment);
    }
    //Update
    public BookingTreatmentDto updateBookingTreatment(Long id, BookingTreatmentDto bookingTreatmentDto) throws RecordNotFoundException {
        Optional<BookingTreatment> bookingTreatmentOptional = bookingTreatmentRepository.findById(id);
        if(bookingTreatmentOptional.isEmpty()){
            throw new RecordNotFoundException("Bookingtreatment is not found with this id " + id);
        }
        BookingTreatment updateBookingTreatment = transferDtoToBookingTreatment((bookingTreatmentDto));
        updateBookingTreatment.setId(id);
        bookingTreatmentRepository.save(updateBookingTreatment);

        return transferBookingTreatmentToDto(updateBookingTreatment);
    }

    /*public BookingTreatmentDto updateBookingTreatment(Long id, BookingTreatmentDto bookingTreatmentDto) {
        BookingTreatment existingBookingTreatment = bookingTreatmentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("There's no booking treatment found with this id: " + id));

        // Update the fields of the existing booking treatment
        existingBookingTreatment.setQuantity(bookingTreatmentDto.getQuantity());
        // You may need to handle updating the treatment and booking references here as well.

        bookingTreatmentRepository.save(existingBookingTreatment);

        return transferBookingTreatmentToDto(existingBookingTreatment);
    }*/

    //Delete
    public void deleteBookingTreatment(Long id) throws RecordNotFoundException {
        Optional<BookingTreatment> optionalBookingTreatment = bookingTreatmentRepository.findById(id);
        if(optionalBookingTreatment.isEmpty()){
            throw new RecordNotFoundException("There is no booking found with id " + id);
        }
        bookingTreatmentRepository.deleteById(id);
    }


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
