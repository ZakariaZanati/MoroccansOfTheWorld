package com.social.controller;


import com.social.model.Notification;
import com.social.model.NotificationType;
import com.social.model.User;
import com.social.repository.NotificationRepository;
import com.social.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/verification")
    public ResponseEntity<List<User>> getUsersToVerify(){

        return ResponseEntity.status(HttpStatus.OK)
                .body(userRepository.findAllByVerificationRequestedAndVerified(true,false));
    }

    @PostMapping("/verification/{id}")
    public ResponseEntity<Void> setVerification(@PathVariable("id") Long id){
        User user = userRepository.findById(id).get();
        user.setVerified(true);
        Notification notification = Notification.builder()
                .notificationType(NotificationType.VERIFICATION)
                .message("You account is now verified")
                .createdDate(Instant.now())
                .user(user)
                .build();
        notificationRepository.save(notification);
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
