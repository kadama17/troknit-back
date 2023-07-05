package com.example.troknite.troknite.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.troknite.troknite.domain.Conversation;
import com.example.troknite.troknite.domain.Message;
import com.example.troknite.troknite.model.HashDTO;
import com.example.troknite.troknite.model.MessageDTO;
import com.example.troknite.troknite.service.ConversationService;
import com.example.troknite.troknite.service.MessageService;

@RestController
@RequestMapping(value = "/api/chat", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")

public class MessageController {

    
    @Autowired
    private MessageService messageService;
    
    @Autowired
    private ConversationService conversationService;


    private List<MessageDTO> messages = new ArrayList<>();

  
   
    @GetMapping("/my-message/{receiver}")
    public List<MessageDTO>  getMyReceivedMessage(@PathVariable String receiver) {
        List<MessageDTO> messages;

    	
    	messages = messageService.getMessagesByReceiver(receiver);
    	  return messages.stream()
                  .map(message -> {
                      MessageDTO messageDTO = new MessageDTO();
                      messageDTO.setId(message.getId());
                      messageDTO.setSender(message.getSender());
                      messageDTO.setReceiver(message.getReceiver());
                      messageDTO.setContent(message.getContent());
                      messageDTO.setType(message.getType());
                      messageDTO.setConversationId(message.getConversationId());
                      messageDTO.setCreationDateTime(message.getCreationDateTime());
                      return messageDTO;
                  })
                  .collect(Collectors.toList());
    }

    
    @PostMapping
    public ResponseEntity<Long> addMessage(@RequestBody MessageDTO messageDTO) {
        Long savedMessage = messageService.create(messageDTO);
        
        Long conversationId = messageDTO.getConversationId();
        
        Conversation conversation = conversationService.getConversationById(conversationId);
        
        MessageDTO message = messageService.getMessageById(savedMessage);
        message.setConversationId(conversation.getId());
        
        messageService.create(message);
        
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }

}
