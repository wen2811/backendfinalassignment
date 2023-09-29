package com.novi.backendfinalassignment.services;

import com.novi.backendfinalassignment.dtos.BookingDto;
import com.novi.backendfinalassignment.exceptions.RecordNotFoundException;
import com.novi.backendfinalassignment.models.Booking;
import com.novi.backendfinalassignment.models.BookingTreatment;
import com.novi.backendfinalassignment.models.Customer;
import com.novi.backendfinalassignment.repositories.BookingRepository;
import com.novi.backendfinalassignment.repositories.BookingTreatmentRepository;
import com.novi.backendfinalassignment.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;




@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingTreatmentRepository bookingTreatmentRepository;
    private final CustomerRepository customerRepository;


    public BookingService(BookingRepository bookingRepository, BookingTreatmentRepository bookingTreatmentRepository, CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingTreatmentRepository = bookingTreatmentRepository;
        this.customerRepository = customerRepository;
    }

    //Read
    public List<BookingDto> getAllBooking() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingDto> bookingDtos = new ArrayList<>();

        for (Booking booking : bookings) {
            bookingDtos.add(transferBookingToDto(booking));
        }
        return bookingDtos;
    }

    public BookingDto getBooking(Long id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);

        if (bookingOptional.isEmpty()){
            throw new RecordNotFoundException("There is no booking found with id: " + id);
        }
        Booking booking = bookingOptional.get();
        return transferBookingToDto(booking);
    }

    //Create
    public Booking createBooking(Long customerId, List<Long> bookingTreatmentIds) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RecordNotFoundException("Customer doesn't exist."));

        Booking booking = new Booking();
        booking.setCustomer(existingCustomer);

        List<BookingTreatment> bookingTreatments = new ArrayList<>();
        for (Long bookingTreatmentId : bookingTreatmentIds) {
            BookingTreatment bookingTreatment = bookingTreatmentRepository.findById(bookingTreatmentId)
                    .orElseThrow(() -> new RecordNotFoundException("BookingTreatment doesn't exist."));
            bookingTreatments.add(bookingTreatment);
        }

        booking.setBookingTreatments(bookingTreatments);
        bookingRepository.save(booking);
        return booking;
    }



    //Update 1 algemeen
    public BookingDto updateBooking(Long id, BookingDto bookingDto)throws RecordNotFoundException {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if(bookingOptional.isEmpty()) {
            throw new RecordNotFoundException("Booking did not find with this id: " + id);
        }
        Booking updateBooking = transferDtoToBooking(bookingDto);
        updateBooking.setId(id);
        bookingRepository.save(updateBooking);
        return transferBookingToDto(updateBooking);
    }

    //Update 2
    public BookingDto updateBookingTreatments(Long id, List<Long> treatmentIds) throws RecordNotFoundException {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isEmpty()) {
            throw new RecordNotFoundException("Booking not found with this id: " + id);
        }
        Booking existingBooking = bookingOptional.get();

        List<BookingTreatment> newBookingTreatments = new ArrayList<>();
        for (Long treatmentId : treatmentIds) {
            BookingTreatment bookingTreatment = bookingTreatmentRepository.findById(treatmentId)
                    .orElseThrow(() -> new RecordNotFoundException("Treatment not found with id: " + treatmentId));
            newBookingTreatments.add(bookingTreatment);
        }

        existingBooking.setBookingTreatments(newBookingTreatments);

        bookingRepository.save(existingBooking);

        return transferBookingToDto(existingBooking);
    }

    // Update 3

    /*public BookingDto updateBooking(Long id, BookingDto bookingDto) throws RecordNotFoundException {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isEmpty()) {
            throw new RecordNotFoundException("Booking not found with this id: " + id);
        }
        Booking existingBooking = bookingOptional.get();

        existingBooking.setDate(bookingDto.getDate());
        existingBooking.setCustomer(bookingDto.getCustomer());
        existingBooking.setBookingStatus(bookingDto.getBookingStatus());
        existingBooking.setInvoice(bookingDto.getInvoice());
        existingBooking.setUser(bookingDto.getEmployee());
        existingBooking.setTotalAmount(bookingDto.getTotalAmount());

        List<BookingTreatment> newBookingTreatments = new ArrayList<>();
        for (BookingTreatment bookingTreatment : bookingDto.getBookingTreatments()) {
            BookingTreatment existingTreatment = bookingTreatmentRepository.findById(bookingTreatment.getId())
                    .orElseThrow(() -> new RecordNotFoundException("Treatment not found with id: " + bookingTreatment.getId()));
            newBookingTreatments.add(existingTreatment);
        }
        existingBooking.setBookingTreatments(newBookingTreatments);

        bookingRepository.save(existingBooking);

        return transferBookingToDto(existingBooking);
    }
*/



    //Delete
    public void deleteBooking(Long id) throws RecordNotFoundException {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if(optionalBooking.isEmpty()){
            throw new RecordNotFoundException("There is no booking found with id " + id);
        }
        bookingRepository.deleteById(id);
    }

    /* public double getTotalAmount(Long id) {
        Optional<Booking> oo = bookingRepository.findById(id);
        if (oo.isPresent()) {
            Booking o = oo.get();
            return o.calculateTotalAmount();
        }
        else{
            throw new RecordNotFoundException("There's no booking found with this id: " + id);
        }
    }*/


    public List<BookingDto> getBookingsForCustomer(Long customerId) throws RecordNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isEmpty()) {
            throw new RecordNotFoundException("Customer not found with ID: " + customerId);
        }
        Customer customer = customerOptional.get();
        List<Booking> bookings = bookingRepository.findByCustomer(customer);
        List<BookingDto> bookingDtos = new ArrayList<>();
        for (Booking booking : bookings) {
            BookingDto bookingDto = transferBookingToDto(booking);
            bookingDtos.add(bookingDto);
        }
        return bookingDtos;
    }


    public BookingDto transferBookingToDto(Booking booking) {
           BookingDto bookingDto = new BookingDto();

        bookingDto.id = booking.getId();
        bookingDto.bookingTreatments = booking.getBookingTreatments();
        bookingDto.date = booking.getDate();
        bookingDto.totalAmount = booking.getTotalAmount();
        bookingDto.bookingStatus = booking.getBookingStatus();
        bookingDto.invoice = booking.getInvoice();
        bookingDto.customer =booking.getCustomer();
        return bookingDto;
    }
    public Booking transferDtoToBooking(BookingDto bookingDto) {
        Booking booking = new Booking();

        bookingDto.setId(bookingDto.id);
        bookingDto.setBookingTreatments(bookingDto.bookingTreatments);
        bookingDto.setDate(bookingDto.date);
        bookingDto.setTotalAmount(bookingDto.totalAmount);
        bookingDto.setBookingStatus(bookingDto.bookingStatus);
        bookingDto.setInvoice(bookingDto.invoice);
        bookingDto.setCustomer(bookingDto.customer);
        return booking;
    }



}

