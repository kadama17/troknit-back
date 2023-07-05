package com.example.troknite.troknite.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.troknite.troknite.model.HashDTO;
import com.example.troknite.troknite.service.HashService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(value = "/api/hashes", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class HashController {

    private final HashService hashService;

    public HashController(HashService hashService) {
        this.hashService = hashService;
    }

    @GetMapping
    public ResponseEntity<List<HashDTO>> getAllHashes() {
        List<HashDTO> hashes = hashService.findAll();
        return ResponseEntity.ok(hashes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashDTO> getHash(@PathVariable Long id) {
        HashDTO hash = hashService.get(id);
        return ResponseEntity.ok(hash);
    }

    @PostMapping
    public ResponseEntity<Long> createHash(@RequestBody @Valid HashDTO hashDTO) {
        Long createdId = hashService.create(hashDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateHash(@PathVariable Long id, @RequestBody @Valid HashDTO hashDTO) {
        hashService.update(id, hashDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHash(@PathVariable Long id) {
        hashService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
