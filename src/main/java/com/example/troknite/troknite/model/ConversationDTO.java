package com.example.troknite.troknite.model;

import java.util.ArrayList;
import java.util.List;

import com.example.troknite.troknite.domain.Message;
import com.example.troknite.troknite.domain.Users;

public class ConversationDTO {
    private Long id;
    private List<Users> participants;
    private List<Message> messages;

    public void Conversation() {
        this.participants = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Users> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Users> participants) {
        this.participants = participants;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public boolean involvesUsers(Users user1, Users user2) {
        return participants.contains(user1) && participants.contains(user2);
    }
}
