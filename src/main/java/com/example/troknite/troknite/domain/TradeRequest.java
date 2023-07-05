package com.example.troknite.troknite.domain;

import com.example.troknite.troknite.model.TradeRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class TradeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String requestProduct;

    @Column(nullable = false)
    private String requestedProduct;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String status;
    
    @Column(nullable = false)
    private String addressOfRequester; 
    
    @Column(nullable = false)
    private String addressOfReceiver; 
    
    @Column
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_requester", referencedColumnName = "id", nullable = false)
    private Users idRequester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_receiver", referencedColumnName = "id", nullable = false)
    private Users idReceiver;

}
