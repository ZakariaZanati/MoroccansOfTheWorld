package com.social.controller;


import com.social.dto.VerificationResponse;
import com.social.model.Notification;
import com.social.model.NotificationType;
import com.social.model.User;
import com.social.repository.NotificationRepository;
import com.social.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/verification")
    public ResponseEntity<List<VerificationResponse>> getUsersToVerify(){
        List<User> users = userRepository.findAllByVerificationRequestedAndVerified(true,false);
        List<VerificationResponse>  verificationResponses = users.stream()
                .map(user -> VerificationResponse.builder()
                    .userId(user.getUserId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .city(user.getCity())
                        .country(user.getCountry())
                        .currentJob(user.getCurrentJob())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .username(user.getUsername())
                .build())
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(verificationResponses);
    }

    @PostMapping("/verification/{id}")
    public ResponseEntity<Void> setVerification(@PathVariable("id") Long id,
                                                @Param("response") String response){
        User user = userRepository.findById(id).get();
        user.setVerified(true);
        Notification notification = Notification.builder()
                .notificationType(NotificationType.VERIFICATION)
                .message("You account is now verified")
                .createdDate(Instant.now())
                .user(user)
                .seen(false)
                .build();
        notificationRepository.save(notification);
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
