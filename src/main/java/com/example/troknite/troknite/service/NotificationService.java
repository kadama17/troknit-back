package com.example.troknite.troknite.service;

import com.example.troknite.troknite.domain.Notification;
import com.example.troknite.troknite.repos.NotificationRepository;
import com.example.troknite.troknite.util.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getUnreadNotifications() {
        return notificationRepository.findByIsRead(false);
    }

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public List<Notification> getNotificationsByType(String type) {
        return notificationRepository.findByType(type);
    }

    public Long createNotification(Notification notification) {
        notification.setDate(LocalDateTime.now());
        notification.setRead(false);
        notification.setConversation(notification.getConversation());
        Notification createdNotification = notificationRepository.save(notification);
        return createdNotification.getId();
    }

    public void markNotificationAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}