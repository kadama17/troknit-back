package com.example.troknite.troknite.service;

import com.example.troknite.troknite.domain.Conversation;
import com.example.troknite.troknite.domain.Message;
import com.example.troknite.troknite.domain.Users;
import com.example.troknite.troknite.exception.ConversationNotFoundException;
import com.example.troknite.troknite.repos.ConversationRepository;

import jakarta.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }



    public Conversation getConversationById(Long id) {
        Conversation conversation = conversationRepository.findById(id)
                .orElseThrow(() -> new ConversationNotFoundException("Conversation not found with id: " + id));

        Hibernate.initialize(conversation.getParticipants());

        return conversation;
    }

    
    public Conversation createConversation(Conversation conversation) {
        return conversationRepository.save(conversation);
    }


    @Transactional
    public void addMessageToConversation(Long conversationId, Message message) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ConversationNotFoundException("Conversation not found with id: " + conversationId));
        conversation.getMessages().add(message);
        conversationRepository.save(conversation);
    }

    public boolean doesConversationInvolveUsers(Long conversationId, Users user1, Users user2) {
        Conversation conversation = getConversationById(conversationId);
        return conversation.involvesUsers(user1, user2);
    }
    
    public Long checkConversationExists(Conversation conversation) {
        List<Users> participants = conversation.getParticipants();

        List<Conversation> existingConversations = getAllConversations();
        for (Conversation existingConversation : existingConversations) {
            List<Users> existingParticipants = existingConversation.getParticipants();

            // Check if the number of participants matches
            if (existingParticipants.size() != participants.size()) {
                continue;
            }

            // Check if the participants in both conversations have the same IDs
            boolean sameParticipants = true;
            for (Users participant : participants) {
                boolean foundParticipant = false;
                for (Users existingParticipant : existingParticipants) {
                    if (existingParticipant.getId().equals(participant.getId())) {
                        foundParticipant = true;
                        break;
                    }
                }

                if (!foundParticipant) {
                    sameParticipants = false;
                    break;
                }
            }

            if (sameParticipants) {
                return existingConversation.getId(); // Return the ID of the existing conversation
            }
        }

        return null; // No conversation with the same participants exists
    }

    
    
    public Long getExistingConversationId(Conversation conversation) {
        List<Users> participants = conversation.getParticipants();

        List<Conversation> existingConversations = getAllConversations();
        for (Conversation existingConversation : existingConversations) {
            List<Users> existingParticipants = existingConversation.getParticipants();

            // Check if the number of participants matches
            if (existingParticipants.size() != participants.size()) {
                continue;
            }

            // Check if the participants in both conversations have the same IDs
            boolean sameParticipants = true;
            for (Users participant : participants) {
                boolean foundParticipant = false;
                for (Users existingParticipant : existingParticipants) {
                    if (existingParticipant.getId().equals(participant.getId())) {
                        foundParticipant = true;
                        break;
                    }
                }

                if (!foundParticipant) {
                    sameParticipants = false;
                    break;
                }
            }

            if (sameParticipants) {
                return existingConversation.getId(); // Return the ID of the existing conversation
            }
        }

        return null; // No conversation with the same participants exists
    }
    
    
    public List<Conversation> getConversationsByParticipantId(Long participantId) {
        List<Conversation> allConversations = getAllConversations();

        List<Conversation> participantConversations = new ArrayList<>();
        for (Conversation conversation : allConversations) {
            List<Users> participants = conversation.getParticipants();
            for (Users participant : participants) {
                if (participant.getId().equals(participantId)) {
                    participantConversations.add(conversation);
                    break;
                }
            }
        }

        return participantConversations;
    }


}
