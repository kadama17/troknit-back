package com.example.troknite.troknite.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.troknite.troknite.domain.Hash;
import com.example.troknite.troknite.domain.Message;
import com.example.troknite.troknite.model.MessageDTO;

public interface MessageRepository  extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndReceiver(String sender, String receiver);
    List<MessageDTO> findBySender(String sender);
    List<Message> findByReceiver(String receiver);


}
