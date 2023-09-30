package com.novi.backendfinalassignment.services;

import com.novi.backendfinalassignment.dtos.FileDto;
import com.novi.backendfinalassignment.exceptions.RecordNotFoundException;
import com.novi.backendfinalassignment.exceptions.ResourceNotFoundException;
import com.novi.backendfinalassignment.exceptions.UnauthorizedAccessException;
import com.novi.backendfinalassignment.models.Customer;
import com.novi.backendfinalassignment.models.File;
import com.novi.backendfinalassignment.models.User;
import com.novi.backendfinalassignment.repositories.CustomerRepository;
import com.novi.backendfinalassignment.repositories.FileRepository;
import com.novi.backendfinalassignment.repositories.UserRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;


@Service
public class FileService {
    private final FileRepository fileRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public FileService(FileRepository fileRepository, CustomerRepository customerRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    public FileDto uploadFile(MultipartFile file, Customer customer) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Het geüploade bestand is leeg.");
        }

        File uploadedFile = new File();
        uploadedFile.setFilename(file.getOriginalFilename());
        uploadedFile.setFiletype(file.getContentType());
        uploadedFile.setData(file.getBytes());
        uploadedFile.setDate(LocalDate.now());
        uploadedFile.setCustomer(customer);

        File savedFile = fileRepository.save(uploadedFile);

        return convertToFileDto(savedFile);
    }


    public FileDto storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Het geüploade bestand is leeg.");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File dbFile = new File();
        dbFile.setFilename(fileName);
        dbFile.setFiletype(file.getContentType());
        dbFile.setData(file.getBytes());
        dbFile.setDate(LocalDate.now());

        File savedFile = fileRepository.save(dbFile);

        return convertToFileDto(savedFile);
    }

    public FileDto getFile(Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("Bestand niet gevonden met id: " + fileId));

        return convertToFileDto(file);
    }

    public FileDto assignFileToCustomer(Long fileId, Long customerId) {
        File file = getFileIfExists(fileId);
        Customer customer = getCustomerIfExists(customerId);

        file.setCustomer(customer);
        File savedFile = fileRepository.save(file);

        return convertToFileDto(savedFile);
    }


    public ResponseEntity<Resource> downloadFile(Long fileId) {
        FileDto fileDto = getFile(fileId);

        ByteArrayResource resource = new ByteArrayResource(fileDto.getData());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fileDto.getFiletype()));
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileDto.getFilename()).build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    public void deleteFile(Long fileId, Long userId) throws UnauthorizedAccessException {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new RecordNotFoundException("Geen bestand gevonden met id: " + fileId));

        User user = (User) userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("Geen gebruiker gevonden met id: " + userId));

        if (!file.getCustomer().getId().equals(user.getCustomer().getId())) {
            throw new UnauthorizedAccessException("Gebruiker is niet gemachtigd om dit bestand te verwijderen");
        }
        fileRepository.deleteById(fileId);
    }

    private File getFileIfExists(Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("Bestand niet gevonden met id: " + fileId));
    }

    private Customer getCustomerIfExists(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Klant niet gevonden met id: " + customerId));
    }

    public FileDto convertToFileDto(File file) {
        FileDto fileDto = new FileDto();
        fileDto.setId(file.getId());
        fileDto.setFilename(file.getFilename());
        fileDto.setFiletype(file.getFiletype());
        fileDto.setDate(file.getDate());
        fileDto.setData(file.getData());
        fileDto.setMimeType(file.getMimeType());
        fileDto.setDescription(file.getDescription());
        fileDto.setCustomer(file.getCustomer());

        return fileDto;
    }


}
/*
        public FileDto transferFileToDto (File file){
            FileDto fileDto = new FileDto();

            fileDto.id = file.getId();
            fileDto.description = file.getDescription();
            fileDto.filename = file.getFilename();
            fileDto.filetype = file.getFiletype();
            fileDto.customer = file.getCustomer();
            fileDto.data = file.getData();
            fileDto.mimeType = file.getMimeType();

            return fileDto;
        }

        public File transferDtoToFile (FileDto fileDto){
            File file = new File();

            file.setId(fileDto.id);
            file.setDescription(fileDto.description);
            file.setFilename(fileDto.filename);
            file.setFiletype(fileDto.filetype);
            file.setData(fileDto.data);
            file.setMimeType(fileDto.mimeType);
            file.setCustomer(fileDto.customer);

            return file;
        }*/


