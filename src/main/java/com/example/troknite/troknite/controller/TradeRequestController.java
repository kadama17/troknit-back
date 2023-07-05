package com.example.troknite.troknite.controller;

import com.example.troknite.troknite.domain.Users;
import com.example.troknite.troknite.model.TradeRequestDTO;
import com.example.troknite.troknite.service.TradeRequestService;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/trade-requests", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class TradeRequestController {

    private  TradeRequestService tradeRequestService;

    public TradeRequestController(final TradeRequestService tradeRequestService) {
        this.tradeRequestService = tradeRequestService;
    }

    @GetMapping
    public ResponseEntity<List<TradeRequestDTO>> getAllTradeRequests() {
        return ResponseEntity.ok(tradeRequestService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TradeRequestDTO> getTradeRequest(@PathVariable final Long id) {
        return ResponseEntity.ok(tradeRequestService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createTradeRequest(@RequestBody @Valid final TradeRequestDTO tradeRequestDTO) {
        final Long createdId = tradeRequestService.create(tradeRequestDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTradeRequest(@PathVariable final Long id,
            @RequestBody @Valid final TradeRequestDTO tradeRequestDTO) {
    	System.out.println(tradeRequestDTO);
        tradeRequestService.update(id, tradeRequestDTO);
        return ResponseEntity.ok().build();
    }
    
    

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTradeRequest1(@PathVariable final Long id, @RequestBody final TradeRequestDTO tradeRequestDTO) {
        tradeRequestService.partialUpdate(id, tradeRequestDTO);
        return ResponseEntity.ok().build();
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTradeRequest(@PathVariable final Long id) {
        tradeRequestService.delete(id);
        return ResponseEntity.noContent().build();
    }

    
    @GetMapping("/receiver/{id}")
    public ResponseEntity<List<TradeRequestDTO>> getTradeRequestsByReceiverId(@PathVariable final Users id) {
        return ResponseEntity.ok(tradeRequestService.findByReceiverId(id));
    }
}
