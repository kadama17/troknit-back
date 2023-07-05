package com.example.troknite.troknite.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.troknite.troknite.domain.TradeRequest;
import com.example.troknite.troknite.domain.Users;

@Repository
public interface TradeRequestRepository extends JpaRepository<TradeRequest, Long> {

	List<TradeRequest> findByIdReceiver(Users receiverId);

}