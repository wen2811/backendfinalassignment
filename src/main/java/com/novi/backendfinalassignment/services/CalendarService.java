package com.novi.backendfinalassignment.services;

import com.novi.backendfinalassignment.dtos.CalendarDto;
import com.novi.backendfinalassignment.exceptions.RecordNotFoundException;
import com.novi.backendfinalassignment.models.Calendar;
import com.novi.backendfinalassignment.repositories.BookingRepository;
import com.novi.backendfinalassignment.repositories.CalendarRepository;
import com.novi.backendfinalassignment.repositories.TreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final TreatmentRepository treatmentRepository;

    private final BookingRepository bookingRepository;


    public CalendarService(CalendarRepository calendarRepository, TreatmentRepository treatmentRepository, BookingRepository bookingRepository) {
        this.calendarRepository = calendarRepository;
        this.treatmentRepository = treatmentRepository;
        this.bookingRepository = bookingRepository;
    }

    //Read
    public List<CalendarDto> getAllCalendar() {
        List<Calendar> calendars = calendarRepository.findAll();
        List<CalendarDto> calendarDtos = new ArrayList<>();

        for (Calendar calendar : calendars) {
            calendarDtos.add(transferCalenderToDto(calendar));
        }
        return calendarDtos;
    }

    public CalendarDto getCalendar(Long id) {
        Optional<Calendar> calendarOptional = calendarRepository.findById(id);
        if (calendarOptional.isPresent()){
            Calendar calendar = calendarOptional.get();
            return transferCalenderToDto(calendar);
        } else {
            throw new RecordNotFoundException("There's no Calendar found with id " + id);
        }
    }

    //method for relation: treatment-calendar
   /* public CalendarDto getCalendarsByTreatment(Long id, CalendarDto calendarDto) throws RecordNotFoundException {
        Optional<Calendar> calendarOptional = calendarRepository.findCalendarByTreatment(id);

        if(calendarOptional.isEmpty()) {
            throw new RecordNotFoundException("There's no calendar to find with this id: " + id);
        }
        Calendar calendar = calendarOptional.get();

        return transferCalendarToDto(calendar);
    }*/




    //Create
    public CalendarDto addCalendar(CalendarDto calendarDto) {
        Calendar calendar = transferDtoToCalendar(calendarDto);
        calendarRepository.save(calendar);

        return transferCalenderToDto(calendar);
    }

    //Update
    public void updateCalendar(Long id, CalendarDto calendarDto) {
        if(!calendarRepository.existsById(id)) {
            throw new RecordNotFoundException("There's no Calendar-timeslot found");
        }
        Calendar updatedCalendar = calendarRepository.findById(id).orElse(null);
        updatedCalendar.setId(calendarDto.getId());
        updatedCalendar.setDate(calendarDto.getDate());
        updatedCalendar.setStartTime(calendarDto.getStartTime());
        updatedCalendar.setEndTime(calendarDto.getEndTime());
        updatedCalendar.setAvailableTime(calendarDto.isAvailableTime());
        calendarRepository.save(updatedCalendar);

    }

    //Delete
    public void deleteCalendar(Long id) {
        calendarRepository.deleteById(id);
    }


    public CalendarDto transferCalenderToDto(Calendar calendar){
        CalendarDto calendarDto = new CalendarDto();

        calendarDto.id = calendar.getId();
        calendarDto.date = calendar.getDate();
        calendarDto.startTime = calendar.getStartTime();
        calendarDto.endTime = calendar.getEndTime();
        calendarDto.availableTime = calendar.isAvailableTime();
        calendarDto.treatment = calendar.getTreatment();
        return calendarDto;
    }


    public Calendar transferDtoToCalendar(CalendarDto calendarDto) {
        Calendar calendar = new Calendar();
        calendar.setDate(calendarDto.date);
        calendar.setStartTime(calendarDto.startTime);
        calendar.setEndTime(calendarDto.endTime);
        calendar.setAvailableTime(calendarDto.availableTime);
        calendar.setTreatment(calendarDto.treatment);
        return calendar;
    }
}


   /* public CalendarDto createCalendar(Long booking_id) {
        Optional<Booking> optionalBooking = bookingRepository.findByBookingId(booking_id);
        if (optionalBooking.isEmpty()) {
            throw new RecordNotFoundException("There is no booking with ID " + booking_id);
        }
        Booking booking = optionalBooking.get();
        boolean timeslotAvailable = calendarRepository.existsById(booking, false);
        if (timeslotAvailable) {
            throw new TimeslotNotAvailableException("Cannot schedule the calendar. The timeslot is unavailable.");
        }
        Calendar newCalendar = new Calendar();
        newCalendar.setBooking(Booking);
        newBooking.setBookingFinished(false);

        Calendar savedCalendar = calendarRepository.save(newCalendar);
        return transferCalenderDto(savedCalendar);
    }

     public CalendarDto createCalendar(Long bookingId, CalendarDto calendarDto) {
        // Zoek de boeking op basis van bookingId
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);

        if (optionalBooking.isEmpty()) {
            throw new RecordNotFoundException("There is no booking with ID " + bookingId);
        }

        Booking booking = optionalBooking.get();

        // Maak een nieuwe kalendergebeurtenis aan en vul deze met gegevens uit de DTO
        Calendar newCalendar = new Calendar();
        newCalendar.setDate(calendarDto.getDate());
        newCalendar.setStartTime(calendarDto.getStartTime());
        newCalendar.setEndTime(calendarDto.getEndTime());
        newCalendar.setAvailableTime(calendarDto.isAvailableTime());
        newCalendar.setBooking(booking);

        // Sla de nieuwe kalendergebeurtenis op in de repository
        Calendar savedCalendar = calendarRepository.save(newCalendar);

        // Retourneer een DTO met de opgeslagen kalendergebeurtenisgegevens
        return transferCalendarDto(savedCalendar);
    }
*/


