package com.social.controller;

import com.social.model.Notification;
import com.social.model.User;
import com.social.repository.NotificationRepository;
import com.social.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private AuthService authService;

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications(){
        User user = authService.getCurrentUser();
        List<Notification> notifications = notificationRepository.findAllByUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(notifications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> setNotificationSeen(@PathVariable("id") Long id){
        Notification notification = notificationRepository.findById(id).get();
        notification.setSeen(true);
        notificationRepository.save(notification);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
