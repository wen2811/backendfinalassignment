package com.novi.backendfinalassignment.services;

import com.novi.backendfinalassignment.dtos.FileDto;
import com.novi.backendfinalassignment.models.Customer;
import com.novi.backendfinalassignment.models.File;
import com.novi.backendfinalassignment.repositories.CustomerRepository;
import com.novi.backendfinalassignment.repositories.FileRepository;
import org.springframework.http.ContentDisposition;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;


@Service
public class FileService {
    private final FileRepository fileRepository;
    private final CustomerRepository customerRepository;

    public FileService(FileRepository fileRepository, CustomerRepository customerRepository) {
        this.fileRepository = fileRepository;
        this.customerRepository = customerRepository;
    }


    public File uploadFile(MultipartFile file, Customer customer) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Het geüploade bestand is leeg.");
        }
        File uploadedFile = new File();
        uploadedFile.setFilename(file.getOriginalFilename());
        uploadedFile.setFiletype(file.getContentType());
        uploadedFile.setData(file.getBytes());
        uploadedFile.setDate(LocalDate.now());
        uploadedFile.setCustomer(customer);

        return fileRepository.save(uploadedFile);
    }

      /*  public ResponseEntity<byte[]> downloadFile(Long fileId) {
            // Zoek het bestand op basis van het bestands-ID
            Optional<File> fileOptional = fileRepository.findById(fileId);

            if (fileOptional.isPresent()) {
                File file = fileOptional.get();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(file.getFiletype()));
                headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getFilename()).build());

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(file.getData());
            } else {
                return ResponseEntity.notFound().build();
            }
        }
*/



    public FileDto transferFileToDto(File file) {
        FileDto fileDto = new FileDto();

        fileDto.id = file.getId();
        fileDto.description = file.getDescription();
        fileDto.filename = file.getFilename();
        fileDto.filetype= file.getFiletype();
        fileDto.customer = file.getCustomer();
        fileDto.data = file.getData();
        fileDto.mimeType = file.getMimeType();

        return fileDto;
    }

    public File transferDtoToFile(FileDto fileDto) {
        File file = new File();

        file.setId(fileDto.id);
        file.setDescription(fileDto.description);
        file.setFilename(fileDto.filename);
        file.setFiletype(fileDto.filetype);
        file.setData(fileDto.data);
        file.setMimeType(fileDto.mimeType);
        file.setCustomer(fileDto.customer);

        return file;
    }
}
