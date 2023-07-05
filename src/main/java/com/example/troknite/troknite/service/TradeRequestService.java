package com.example.troknite.troknite.service;

import com.example.troknite.troknite.domain.TradeRequest;
import com.example.troknite.troknite.domain.Users;
import com.example.troknite.troknite.model.TradeRequestDTO;
import com.example.troknite.troknite.repos.TradeRequestRepository;
import com.example.troknite.troknite.repos.UsersRepository;
import com.example.troknite.troknite.util.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TradeRequestService {

    private final TradeRequestRepository tradeRequestRepository;
    private final UsersRepository usersRepository;

    public TradeRequestService(final TradeRequestRepository tradeRequestRepository,
            final UsersRepository usersRepository) {
        this.tradeRequestRepository = tradeRequestRepository;
        this.usersRepository = usersRepository;
    }

    public List<TradeRequestDTO> findAll() {
        final List<TradeRequest> tradeRequests = tradeRequestRepository.findAll(Sort.by("id"));
        return tradeRequests.stream()
                .map((tradeRequest) -> mapToDTO(tradeRequest, new TradeRequestDTO()))
                .toList();
    }

    public TradeRequestDTO get(final Long id) {
        return tradeRequestRepository.findById(id)
                .map(tradeRequest -> mapToDTO(tradeRequest, new TradeRequestDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public void partialUpdate(Long id, TradeRequestDTO tradeRequestDTO) {
        TradeRequest tradeRequest = tradeRequestRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        if (tradeRequestDTO.getStatus() != null) {
            tradeRequest.setStatus(tradeRequestDTO.getStatus());
        }

        if (tradeRequestDTO.getAddressOfReceiver() != null) {
            tradeRequest.setAddressOfReceiver(tradeRequestDTO.getAddressOfReceiver());
        }

        tradeRequestRepository.save(tradeRequest);
    }

    public Long create(final TradeRequestDTO tradeRequestDTO) {
        final TradeRequest tradeRequest = new TradeRequest();
        mapToEntity(tradeRequestDTO, tradeRequest);
        return tradeRequestRepository.save(tradeRequest).getId();
    }

    public void update(final Long id, final TradeRequestDTO tradeRequestDTO) {
        final TradeRequest tradeRequest = tradeRequestRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(tradeRequestDTO, tradeRequest);
        if(tradeRequestDTO.getRequestProduct()!= null) {
        	tradeRequest.setRequestProduct(tradeRequestDTO.getRequestProduct());
        }	
        if(tradeRequestDTO.getRequestedProduct() != null) {
            tradeRequest.setRequestedProduct(tradeRequestDTO.getRequestedProduct());
        }

        if(tradeRequestDTO.getDate() != null) {
            tradeRequest.setDate(tradeRequestDTO.getDate());
        }

        if(tradeRequestDTO.getStatus() != null) {
            tradeRequest.setStatus(tradeRequestDTO.getStatus());
        }
        if(tradeRequestDTO.getIdRequester() != null) {
            Users requester = usersRepository.findById(tradeRequestDTO.getIdRequester())
                    .orElseThrow(NotFoundException::new);
            tradeRequest.setIdRequester(requester);
        }

        if(tradeRequestDTO.getIdReceiver() != null) {
            Users receiver = usersRepository.findById(tradeRequestDTO.getIdReceiver())
                    .orElseThrow(NotFoundException::new);
            tradeRequest.setIdReceiver(receiver);
        }
        
        tradeRequestRepository.save(tradeRequest);
    }

    public void delete(final Long id) {
        tradeRequestRepository.deleteById(id);
    }

    private TradeRequestDTO mapToDTO(final TradeRequest tradeRequest, final TradeRequestDTO tradeRequestDTO) {
        tradeRequestDTO.setId(tradeRequest.getId());
        tradeRequestDTO.setRequestProduct(tradeRequest.getRequestProduct());
        tradeRequestDTO.setRequestedProduct(tradeRequest.getRequestedProduct());
        tradeRequestDTO.setDate(tradeRequest.getDate());
        tradeRequestDTO.setStatus(tradeRequest.getStatus());
        tradeRequestDTO.setComment(tradeRequest.getComment());
        tradeRequestDTO.setIdRequester(tradeRequest.getIdRequester() == null ? null : tradeRequest.getIdRequester().getId());
        tradeRequestDTO.setIdReceiver(tradeRequest.getIdReceiver() == null ? null : tradeRequest.getIdReceiver().getId());
        return tradeRequestDTO;
    }

private TradeRequest mapToEntity(final TradeRequestDTO tradeRequestDTO, final TradeRequest tradeRequest) {
        tradeRequest.setRequestProduct(tradeRequestDTO.getRequestProduct());
        tradeRequest.setRequestedProduct(tradeRequestDTO.getRequestedProduct());
        tradeRequest.setDate(tradeRequestDTO.getDate());
        tradeRequest.setStatus(tradeRequestDTO.getStatus());
        tradeRequest.setAddressOfRequester(tradeRequestDTO.getAddressOfRequester());
        tradeRequest.setAddressOfReceiver(tradeRequestDTO.getAddressOfReceiver());
        tradeRequest.setComment(tradeRequestDTO.getComment());
        tradeRequest.setIdRequester(tradeRequestDTO.getIdRequester() == null ? null : usersRepository.findById(tradeRequestDTO.getIdRequester())
                .orElseThrow(() -> new NotFoundException("Requester not found")));
        tradeRequest.setIdReceiver(tradeRequestDTO.getIdReceiver() == null ? null : usersRepository.findById(tradeRequestDTO.getIdReceiver())
                .orElseThrow(() -> new NotFoundException("Receiver not found")));
        return tradeRequest;
    }

public List<TradeRequestDTO> findByReceiverId(Users receiverId) {
    List<TradeRequest> tradeRequests = tradeRequestRepository.findByIdReceiver(receiverId);
    List<TradeRequestDTO> tradeRequestDTOs = new ArrayList<>();
    for (TradeRequest tradeRequest : tradeRequests) {
        TradeRequestDTO tradeRequestDTO = convertToDto(tradeRequest);
        tradeRequestDTOs.add(tradeRequestDTO);
    }
    return tradeRequestDTOs;
}

private TradeRequestDTO convertToDto(TradeRequest tradeRequest) {
    TradeRequestDTO tradeRequestDTO = new TradeRequestDTO();
    tradeRequestDTO.setId(tradeRequest.getId());
    tradeRequestDTO.setRequestProduct(tradeRequest.getRequestProduct());
    tradeRequestDTO.setRequestedProduct(tradeRequest.getRequestedProduct());
    tradeRequestDTO.setDate(tradeRequest.getDate());
    tradeRequestDTO.setStatus(tradeRequest.getStatus());
    tradeRequestDTO.setAddressOfRequester(tradeRequest.getAddressOfRequester());
    tradeRequestDTO.setAddressOfReceiver(tradeRequest.getAddressOfReceiver());
    tradeRequestDTO.setIdRequester(tradeRequest.getIdRequester().getId());
    tradeRequestDTO.setIdReceiver(tradeRequest.getIdReceiver().getId());
    return tradeRequestDTO;
}


}
