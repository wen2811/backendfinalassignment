package com.novi.backendfinalassignment.services;

import com.novi.backendfinalassignment.dtos.BookingDto;
import com.novi.backendfinalassignment.exceptions.RecordNotFoundException;
import com.novi.backendfinalassignment.models.Booking;
import com.novi.backendfinalassignment.models.Customer;
import com.novi.backendfinalassignment.models.Treatment;
import com.novi.backendfinalassignment.repositories.BookingRepository;
import com.novi.backendfinalassignment.repositories.CustomerRepository;
import com.novi.backendfinalassignment.repositories.TreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;




@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final TreatmentRepository treatmentRepository;
    private final CustomerRepository customerRepository;


    public BookingService(BookingRepository bookingRepository, TreatmentRepository treatmentRepository, CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.treatmentRepository = treatmentRepository;
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
    /*public BookingDto addBooking(BookingDto bookingDto) {
        Booking booking = transferDtoToBooking(bookingDto);
        bookingRepository.save(booking);

        return transferBookingToDto(booking);
    }*/


    public Booking createBooking(Long customerId, List<Long> treatmentIds) {
        Customer existingCustomer = customerRepository.findById(customerId).orElse(null);

        if (existingCustomer == null) {
            throw new RecordNotFoundException("Customer doesn't excist.");
        }
        Booking booking = new Booking();
        booking.setCustomer(existingCustomer);

        List<Treatment> treatments = new ArrayList<>();
        for (Long treatmentId : treatmentIds) {
            treatmentRepository.findById(treatmentId).ifPresent(treatments::add);
        }
        booking.setTreatment(treatments);
        bookingRepository.save(booking);
        return booking;
    }




    //Update
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


    //Delete
    public void deleteBooking(Long id) throws RecordNotFoundException {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if(optionalBooking.isEmpty()){
            throw new RecordNotFoundException("There is no booking found with id " + id);
        }
        bookingRepository.deleteById(id);
    }

    public double getTotalAmount(Long id) {
        Optional<Booking> oo = bookingRepository.findById(id);
        if (oo.isPresent()) {
            Booking o = oo.get();
            return o.calculateTotalAmount();
        }
        else{
            throw new RecordNotFoundException("There's no booking found with this id: " + id);
        }
    }


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
        bookingDto.treatment = booking.getTreatment();
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
        bookingDto.setTreatment(bookingDto.treatment);
        bookingDto.setDate(bookingDto.date);
        bookingDto.setTotalAmount(bookingDto.totalAmount);
        bookingDto.setBookingStatus(bookingDto.bookingStatus);
        bookingDto.setInvoice(bookingDto.invoice);
        bookingDto.setCustomer(bookingDto.customer);
        return booking;
    }



}

