package com.novi.backendfinalassignment.controllers;

import com.novi.backendfinalassignment.models.Customer;
import com.novi.backendfinalassignment.models.File;
import com.novi.backendfinalassignment.services.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("customerId") Long customerId) throws IOException {
        Customer customer = new Customer();
        customer.setId(customerId);

        File uploadedFile = fileService.uploadFile(file, customer);

        return ResponseEntity.ok("File is successful uploaded ID: " + uploadedFile.getId());

    }

   /* @PostMapping("/single/uploadeDb")
    public ResponseEntity<String> singleFileUploadToDb(@RequestParam("file") MultipartFile file) throws IOException {
        fileUploadService.uploadFile(file);

        return ResponseEntity.ok("Bestand succesvol geüpload naar de database");
    }
    @GetMapping
    public ResponseEntity<byte[]> downloadSingleFile(@PathVariable String fileName){
        File file = fileRepository.findByFilename(fileName);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename = " + file.getFilename())
                .body(file.getDocfile());
    }

    @GetMapping("/downloadFromDb/{fileId}")
    public ResponseEntity<byte[]> downloadSingleFile(@PathVariable Long fileId) {

        File file = fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException());

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + file.getFilename())
                .body(file.getDocfile());
    }

    @GetMapping("/downloadFromDb/{fileId}")
    public ResponseEntity<byte[]> downloadSingleFile(@PathVariable Long fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException());
        byte[] docFile = file.getDocfile();

        if (docFile == null) {
            throw new RuntimeException("there is no file yet.");
        }
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "file" + file.getFilename() + ".png");
        headers.setContentLength(docFile.length);
        return new ResponseEntity<>(docFile, headers, HttpStatus.OK);
    }

        @GetMapping("/downloadFromDb/{fileId}")
        public ResponseEntity<byte[]> downloadSingleFile(@PathVariable Long fileId) {
            File file = fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException());
            byte[] docFile = file.getDocfile();

            if (docFile == null) {
            throw new RuntimeException("there is no file yet.");
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentDispositionFormData("attachment", "file" + file.getFilename() + ".png");
            headers.setContentLength(docFile.length);
            return new ResponseEntity<>(docFile, headers, HttpStatus.OK);
    }*/
}



