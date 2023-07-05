package com.example.troknite.troknite.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
	    private Long id;
	    private String type;
	    private Long idOfSender;
	    private Long IdOfReceiver;
	    private boolean isRead;
	    private LocalDateTime date;
	 
}
