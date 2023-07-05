package com.example.troknite.troknite.service;


import com.example.troknite.troknite.domain.Conversation;
import com.example.troknite.troknite.domain.Message;
import com.example.troknite.troknite.model.MessageDTO;
import com.example.troknite.troknite.repos.MessageRepository;
import com.example.troknite.troknite.util.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.processing.Messager;

@Service
public class MessageService {

	
    private final MessageRepository messageRepository;
    
    @Autowired
    private final ConversationService conversationService;

    public MessageService(MessageRepository messageRepository, ConversationService conversationService) {
        this.messageRepository = messageRepository;
        this.conversationService= conversationService;
    }

    public List<MessageDTO> findAll() {
        List<Message> messages = messageRepository.findAll();
        return messages.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public MessageDTO get(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return mapToDTO(message);
    }

    public List<MessageDTO> getMessagesBySenderAndReceiver(String sender, String receiver) {
        List<Message> messages = messageRepository.findBySenderAndReceiver(sender, receiver);
        List<MessageDTO> messageDTOs = new ArrayList<>();

        for (Message message : messages) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setId(message.getId());
            messageDTO.setSender(message.getSender());
            messageDTO.setReceiver(message.getReceiver());
            messageDTO.setContent(message.getContent());
            messageDTO.setType(message.getType());
            messageDTO.setCreationDateTime(message.getCreationDateTime());   
            messageDTOs.add(messageDTO);

        }
		return messageDTOs;


    }
    public List<MessageDTO> getMessagesBySender(String sender) {
        return messageRepository.findBySender(sender);
    }
    public List<MessageDTO> getMessagesByReceiver(String receiver) {
    	
        List<Message> messages = messageRepository.findByReceiver(receiver);
        List<MessageDTO> messageDTOs = new ArrayList<>();

         for (Message message : messages) {
             MessageDTO messageDTO = new MessageDTO();
             messageDTO.setId(message.getId());
             messageDTO.setSender(message.getSender());
             messageDTO.setReceiver(message.getReceiver());
             messageDTO.setContent(message.getContent());
             messageDTO.setType(message.getType());
             messageDTO.setCreationDateTime(message.getCreationDateTime());   
             messageDTO.setConversationId(message.getConversation().getId());;
             messageDTOs.add(messageDTO);

         }
 		return messageDTOs;
    }
    
    
    public Long create(MessageDTO messageDTO) {
        Message message = new Message();
        mapToEntity(messageDTO, message);
        message.setCreationDateTime(LocalDateTime.now()); // Ajout de la date et de l'heure actuelles
        Message createdMessage = messageRepository.save(message);
        return createdMessage.getId();
    }

    public void update(Long id, MessageDTO messageDTO) {
        Message message = messageRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(messageDTO, message);
        messageRepository.save(message);
    }

    public void delete(Long id) {
        messageRepository.deleteById(id);
    }

    private MessageDTO mapToDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setContent(message.getContent());
        messageDTO.setReceiver(message.getReceiver());
        messageDTO.setSender(message.getSender());
        messageDTO.setType(message.getType());
        messageDTO.setConversationId(message.getConversation().getId());
        messageDTO.setCreationDateTime(LocalDateTime.now()); 

        return messageDTO;
    }

    private void mapToEntity(MessageDTO messageDTO, Message message) {
        message.setContent(messageDTO.getContent());
        message.setReceiver(messageDTO.getReceiver());
        message.setSender(messageDTO.getSender());
        message.setType(messageDTO.getType());
        Conversation conversation = conversationService.getConversationById(messageDTO.getConversationId());
        message.setConversation(conversation);

        System.out.println(message);

    }
    public MessageDTO getMessageById(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return mapToDTO(message);
    }

}
