package com.example.troknite.troknite.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.troknite.troknite.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByIsRead(boolean isRead);
    List<Notification> findByType(String type);
}
