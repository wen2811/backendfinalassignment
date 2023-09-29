package com.novi.backendfinalassignment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.config.Task;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    private String filetype;
    private LocalDate date;
    @Lob
    private byte[] data;
    private String mimeType;
    private String description;

    @ManyToOne
    @JsonIgnore
    private Customer customer;



}




