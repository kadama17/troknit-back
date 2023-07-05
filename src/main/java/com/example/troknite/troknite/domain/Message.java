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
public class Message {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    @JsonBackReference
    private Conversation conversation;

    @Column
    private Long sender;

    @Column
    private Long receiver;
    
    @Column
    private String content;
    
    @Column
    private LocalDateTime creationDateTime;
    
    @Column
    private String type;

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
    // Constructors, getters, and setters
    
    
}
