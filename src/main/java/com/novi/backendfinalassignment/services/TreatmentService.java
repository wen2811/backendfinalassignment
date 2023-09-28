package com.novi.backendfinalassignment.services;

import com.novi.backendfinalassignment.dtos.CalendarDto;
import com.novi.backendfinalassignment.dtos.TreatmentDto;
import com.novi.backendfinalassignment.exceptions.RecordNotFoundException;
import com.novi.backendfinalassignment.models.Calendar;
import com.novi.backendfinalassignment.models.Treatment;
import com.novi.backendfinalassignment.models.TreatmentType;
import com.novi.backendfinalassignment.repositories.CalendarRepository;
import com.novi.backendfinalassignment.repositories.TreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TreatmentService {
    private final TreatmentRepository treatmentRepository;
    private final CalendarRepository calendarRepository;


    public TreatmentService(TreatmentRepository treatmentRepository, CalendarRepository calendarRepository) {
        this.treatmentRepository = treatmentRepository;
        this.calendarRepository = calendarRepository;
    }
    
    //Read
    public List<TreatmentDto> getAllTreatments() {
        Iterable<Treatment> treatments = treatmentRepository.findAll();
        List<TreatmentDto> treatmentDtos = new ArrayList<>();

        for (Treatment treatment: treatments) {
            treatmentDtos.add(transferTreatmentToDto(treatment));
        }

        return treatmentDtos;
    }


    public TreatmentDto getTreatmentsByType(TreatmentType type) throws RecordNotFoundException {
        Optional<Treatment> treatmentOptional = treatmentRepository.findTreatmentsByType(type);

        if(treatmentOptional.isEmpty()) {
            throw new RecordNotFoundException("There's no treatment to find with this type: " + type);
        }
        Treatment treatment = treatmentOptional.get();

        return transferTreatmentToDto(treatment);
    }


    //Create
    public TreatmentDto addTreatment(TreatmentDto treatmentDto) {
        Treatment treatment = transferDtoToTreatment(treatmentDto);
        treatmentRepository.save(treatment);

        return transferTreatmentToDto(treatment);
    }

    //Update
    public void updateTreatment(Long id, TreatmentDto treatmentDto) {
        Treatment existingTreatment = treatmentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("There's no treatment found with this id " + id));
        if (treatmentDto.getDescription() != null) {
            existingTreatment.setDescription(treatmentDto.getDescription());
        }
        if (treatmentDto.getDuration() != 0.0) {
            existingTreatment.setDuration(treatmentDto.getDuration());
        }
        if (treatmentDto.getName() != null) {
            existingTreatment.setName(treatmentDto.getName());
        }
        if (treatmentDto.getType() != null) {
            existingTreatment.setType(treatmentDto.getType());
        }
        if (treatmentDto.getPrice() != 0.0) {
            existingTreatment.setPrice(treatmentDto.getPrice());
        }
        treatmentRepository.save(existingTreatment);
    }



    //Delete
        public void deleteTreatment(Long id) throws RecordNotFoundException {
            Optional<Treatment> optionalTreatment = treatmentRepository.findById(id);
            if(optionalTreatment.isEmpty()) {
                throw new RecordNotFoundException("There is no booking found with id: " + id);
            }
            Treatment treatment = optionalTreatment.get();
            treatmentRepository.delete(treatment);
        }


    public TreatmentDto updateTreatmentWithCalendar(Long id, TreatmentDto treatmentDto, CalendarDto calendarDto) {
        Optional<Treatment> treatmentOptional = treatmentRepository.findById(id);

        if (treatmentOptional.isEmpty()) {
            throw new RecordNotFoundException("There's no treatment found with this ID: " + id);
        }
        Treatment treatment = treatmentOptional.get();
        if (treatmentDto != null) {
            if (treatmentDto.getDescription() != null) {
                treatment.setDescription(treatmentDto.getDescription());
            }
            if (treatmentDto.getDuration() != 0.0) {
                treatment.setDuration(treatmentDto.getDuration());
            }
            if (treatmentDto.getName() != null) {
                treatment.setName(treatmentDto.getName());
            }
            if (treatmentDto.getType() != null) {
                treatment.setType(treatmentDto.getType());
            }
            if (treatmentDto.getPrice() != 0.0) {
                treatment.setPrice(treatmentDto.getPrice());
            }
        }
        Calendar calendar = treatment.getCalendar();
        if (calendarDto != null) {
            if (calendarDto.getDate() != null) {
                calendar.setDate(calendarDto.getDate());
            }
            if (calendarDto.getStartTime() != null) {
                calendar.setStartTime(calendarDto.getStartTime());
            }
            if (calendarDto.getEndTime() != null) {
                calendar.setEndTime(calendarDto.getEndTime());
            }
        }
        treatmentRepository.save(treatment);
        calendarRepository.save(calendar);

        return transferTreatmentToDto(treatment);
    }

    public TreatmentDto getTreatmentWithCalendar(Long id) {
        Optional<Treatment> treatmentOptional = treatmentRepository.findById(id);

        if (treatmentOptional.isEmpty()) {
            throw new RecordNotFoundException("There's no treatment found with this ID: " + id);
        }

        Treatment treatment = treatmentOptional.get();
        Calendar calendar = treatment.getCalendar();

        if (calendar == null) {
            throw new RecordNotFoundException("There's no calendar associated with this treatment.");
        }

        return transferTreatmentToDto(treatment);
    }




    /*public double getSum(Long id) {
        Optional<Treatment> oo = treatmentRepository.findById(id);
        if (oo.isPresent()) {
            Treatment o = oo.get();
            return o.calculateSum();
        }
        else{
            throw new RecordNotFoundException("There's no treatment found with this id: " + id);
        }
    }*/


        public TreatmentDto transferTreatmentToDto (Treatment treatment){
            TreatmentDto treatmentDto = new TreatmentDto();

            treatmentDto.id = treatment.getId();
            treatmentDto.description = treatment.getDescription();
            treatmentDto.duration = treatment.getDuration();
            treatmentDto.name = treatment.getName();
            treatmentDto.type = treatment.getType();
            treatmentDto.price = treatment.getPrice();
            treatmentDto.calendar =treatment.getCalendar();
            return treatmentDto;
        }
        public Treatment transferDtoToTreatment (TreatmentDto treatmentDto){
            Treatment treatment = new Treatment();

            treatmentDto.setId(treatmentDto.id);
            treatmentDto.setDescription(treatmentDto.description);
            treatmentDto.setDuration(treatmentDto.duration);
            treatmentDto.setName(treatmentDto.name);
            treatmentDto.setType(treatmentDto.type);
            treatmentDto.setPrice(treatmentDto.price);
            treatmentDto.setCalendar(treatmentDto.calendar);
            return treatment;
        }


}


