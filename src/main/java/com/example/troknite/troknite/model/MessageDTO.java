package com.example.troknite.troknite.model;


import java.time.LocalDateTime;

import com.example.troknite.troknite.domain.Conversation;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
		private Long id;
		private Long sender;
	    private Long receiver;
	    private String content;
	    private LocalDateTime creationDateTime;
	    private String type;
	    private Long conversationId;



}
