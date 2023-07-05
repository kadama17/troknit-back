package com.example.troknite.troknite.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeRequestDTO {

    private Long id;

    @Size(max = 255)
    private String requestProduct;

    @Size(max = 255)
    private String requestedProduct;

    private String date;

    private String status;

    private String addressOfRequester;
    
    private String addressOfReceiver;
    
    private Long idRequester;

    private Long idReceiver;
    
    private String comment;

}

