package com.novi.backendfinalassignment.controllers;

import com.novi.backendfinalassignment.dtos.FileDto;
import com.novi.backendfinalassignment.exceptions.UnauthorizedAccessException;
import com.novi.backendfinalassignment.models.Customer;
import com.novi.backendfinalassignment.models.File;
import com.novi.backendfinalassignment.services.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileDto> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("customerId") Long customerId) throws IOException {
        Customer customer = new Customer();
        customer.setId(customerId);

        FileDto uploadedFile = fileService.uploadFile(file, customer);

        return ResponseEntity.ok(uploadedFile);
    }

    @PostMapping("/store")
    public ResponseEntity<FileDto> storeFile(@RequestParam("file") MultipartFile file) throws IOException {
        FileDto uploadedFile = fileService.storeFile(file);

        return ResponseEntity.ok(uploadedFile);
    }

    @GetMapping("/get/{fileId}")
    public ResponseEntity<FileDto> getFile(@PathVariable Long fileId) {
        FileDto fileDto = fileService.getFile(fileId);
        return ResponseEntity.ok(fileDto);
    }

    @PostMapping("/assign/{fileId}/to-customer/{customerId}")
    public ResponseEntity<FileDto> assignFileToCustomer(@PathVariable Long fileId, @PathVariable Long customerId) {
        FileDto assignedFile = fileService.assignFileToCustomer(fileId, customerId);
        return ResponseEntity.ok(assignedFile);
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        ResponseEntity<Resource> fileResponse = fileService.downloadFile(fileId);
        return fileResponse;
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Object> deleteFile(@PathVariable Long fileId, @RequestParam Long userId) throws UnauthorizedAccessException {
        fileService.deleteFile(fileId, userId);
        return ResponseEntity.noContent().build();
    }




}



