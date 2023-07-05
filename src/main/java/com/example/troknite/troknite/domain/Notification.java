package com.example.troknite.troknite.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String type;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    @JsonBackReference
    private Conversation conversation;

    @Column
    private LocalDateTime date;
    
    @Column 
    private Long idOfSender;
    
    @Column
    private Long IdOfReceiver;


    @Column
    private boolean isRead;

    // Constructors, getters, and setters
}