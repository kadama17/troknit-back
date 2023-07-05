package com.example.troknite.troknite.service;

import com.example.troknite.troknite.domain.Hash;
import com.example.troknite.troknite.model.HashDTO;
import com.example.troknite.troknite.repos.HashRepository;
import com.example.troknite.troknite.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HashService {

    private final HashRepository hashRepository;

    public HashService(HashRepository hashRepository) {
        this.hashRepository = hashRepository;
    }

    public List<HashDTO> findAll() {
        List<Hash> hashes = hashRepository.findAll();
        return hashes.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public HashDTO get(Long id) {
        Hash hash = hashRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return mapToDTO(hash);
    }

    public Long create(HashDTO hashDTO) {
        Hash hash = new Hash();
        mapToEntity(hashDTO, hash);
        Hash createdHash = hashRepository.save(hash);
        return createdHash.getId();
    }

    public void update(Long id, HashDTO hashDTO) {
        Hash hash = hashRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(hashDTO, hash);
        hashRepository.save(hash);
    }

    public void delete(Long id) {
        hashRepository.deleteById(id);
    }

    private HashDTO mapToDTO(Hash hash) {
        HashDTO hashDTO = new HashDTO();
        hashDTO.setId(hash.getId());
        hashDTO.setHash(hash.getHash());
        return hashDTO;
    }

    private void mapToEntity(HashDTO hashDTO, Hash hash) {
        hash.setHash(hashDTO.getHash());
        // You can map the transfer field here if needed
    }
}
