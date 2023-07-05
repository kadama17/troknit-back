package com.example.troknite.troknite.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.List;
import jakarta.persistence.JoinColumn;


import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Conversation {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "conversation")
    @JsonManagedReference
    private List<Message> messages;

    @ManyToMany
    @JoinTable(
        name = "user_conversation",
        joinColumns = @JoinColumn(name = "conversation_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Users> participants;    
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
        message.setConversation(this);
    }

    @SuppressWarnings("unlikely-arg-type")
    public boolean involvesUsers(Users user1, Users user2) {
        System.out.println(user1.getId());
        return participants.stream().anyMatch(user -> user.getId().equals(user1.getId()))
                && participants.stream().anyMatch(user -> user.getId().equals(user2.getId()));
    }

}
