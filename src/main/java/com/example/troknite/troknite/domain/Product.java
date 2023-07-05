package com.example.troknite.troknite.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "\"description\"")
    private String description;

    @Column(nullable = false)
    private String category; 
    
    @Column(nullable = false)
    private Double value;

    @Column
    private Boolean available;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;
    
    @Lob
    @Column(length = 20971520)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<byte[]> images;


}
