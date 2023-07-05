package com.example.troknite.troknite.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.troknite.troknite.domain.Notification;
import com.example.troknite.troknite.service.NotificationService;

@RestController
@RequestMapping(value="/api/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping("/subscribe")
    public SseEmitter subscribeToNotifications() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // Set a longer timeout value
        emitters.add(emitter); // Ajouter l'émetteur à la liste
        emitter.onCompletion(() -> emitters.remove(emitter)); // Supprimer l'émetteur lorsqu'il est terminé
        return emitter;
    }
    // Méthode pour notifier les émetteurs SSE lorsqu'il y a une nouvelle notification
    private void notifySubscribers(Notification notification) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        emitters.forEach(emitter -> {
            try {
                emitter.send(notification, MediaType.APPLICATION_JSON); // Envoyer la notification
            } catch (Exception e) {
                deadEmitters.add(emitter); // Ajouter l'émetteur mort à la liste pour le supprimer ultérieurement
            }
        });
        emitters.removeAll(deadEmitters); // Supprimer les émetteurs morts
    }
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications() {
        return notificationService.getUnreadNotifications();
    }

    @GetMapping("/message")
    public List<Notification> getMessageNotifications() {
        return notificationService.getNotificationsByType("message");
    }

    @GetMapping("/request")
    public List<Notification> getRequestNotifications() {
        return notificationService.getNotificationsByType("request");
    }

    @PostMapping
    public ResponseEntity<Long> createNotification(@RequestBody Notification notification) {
        Long savedNotificationId = notificationService.createNotification(notification);
        notifySubscribers(notification); // Envoyer la nouvelle notification aux émetteurs SSE
        return new ResponseEntity<>(savedNotificationId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable("id") Long id) {
        notificationService.markNotificationAsRead(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
