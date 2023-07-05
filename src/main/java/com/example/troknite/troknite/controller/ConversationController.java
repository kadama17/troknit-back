package com.example.troknite.troknite.controller;

import com.example.troknite.troknite.domain.Conversation;
import com.example.troknite.troknite.domain.Message;
import com.example.troknite.troknite.domain.Users;
import com.example.troknite.troknite.model.MessageDTO;
import com.example.troknite.troknite.service.ConversationService;
import com.example.troknite.troknite.service.MessageService;
import com.example.troknite.troknite.service.UsersService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/conversations",  produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("http://localhost:3000")
public class ConversationController {

    private final ConversationService conversationService;
    private final UsersService usersService;
    private final MessageService messageService;
    
    public ConversationController(ConversationService conversationService, UsersService usersService, MessageService messageService) {
        this.conversationService = conversationService;
        this.usersService = usersService;
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<Conversation>> getAllConversations() {
        List<Conversation> conversations = conversationService.getAllConversations();
        return ResponseEntity.ok(conversations);
    }

    @GetMapping("/{participantId}/conversations")
    public ResponseEntity<List<Conversation>> getConversationsByParticipantId(@PathVariable Long participantId) {
        List<Conversation> participantConversations = conversationService.getConversationsByParticipantId(participantId);
        return ResponseEntity.ok(participantConversations);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Conversation> getConversationById(@PathVariable Long id) {
        Conversation conversation = conversationService.getConversationById(id);
        return ResponseEntity.ok(conversation);
    }

    @PostMapping
    public ResponseEntity<Long> createConversation(@RequestBody Conversation conversation) {
        // Check if a conversation exists already with the same participants
        Long existingConversationId = conversationService.getExistingConversationId(conversation);

        if (existingConversationId != null) {
            // Return a response indicating that the conversation already exists
            return ResponseEntity.ok(existingConversationId);
        } else {
            // Create a new conversation
            Conversation createdConversation = conversationService.createConversation(conversation);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdConversation.getId());
        }
    }

    @PostMapping("/{conversationId}/messages")
    public ResponseEntity<Conversation> addMessageToConversation(
            @PathVariable Long conversationId,
            @RequestBody MessageDTO message) {
    	
    	System.out.println(message);

    		MessageDTO msg = new MessageDTO();
    		message.setConversationId(conversationId);
    		
    		msg.setContent(message.getContent());
    		msg.setReceiver(message.getReceiver());
    		msg.setSender(message.getSender());
    		msg.setType(message.getType());
    		msg.setConversationId(conversationId);
    		
    	
        Users user1 = usersService.getUserById(msg.getSender());
        Users user2 = usersService.getUserById(msg.getReceiver());
       

        boolean conversationExists = conversationService.doesConversationInvolveUsers(conversationId, user1, user2);
        System.out.println(conversationExists);

        if (!conversationExists) {
            return ResponseEntity.badRequest().build();
        }
        
		System.out.println(msg);

        messageService.create(msg);
        return ResponseEntity.ok().build();
    }
}
