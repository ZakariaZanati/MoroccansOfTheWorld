package com.social.controller;


import com.social.dto.UserInfos;
import com.social.mapper.UserMapper;
import com.social.model.User;
import com.social.model.UserConnection;
import com.social.repository.UserRepository;
import com.social.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/connections")
public class ConnectionController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/request/{id}")
    private ResponseEntity<Void> sendConnectionRequest(@PathVariable Long id){
        User user = authService.getCurrentUser();
        User requestedUser = userRepository.findById(id).get();
        UserConnection connection = new UserConnection(user,requestedUser, UserConnection.ConnectionStatus.REQUESTED,false);
        System.out.println(connection.toString());
        Set<UserConnection> connections = new HashSet<>();
        connections.add(connection);
        //user.setRequestedConnections(connections);
        //userRepository.save(user);
        requestedUser.setReceivedConnections(connections);
        userRepository.save(requestedUser);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/requests")
    private ResponseEntity<List<UserInfos>> viewConnectionRequests(){
        User user = authService.getCurrentUser();
        Set<UserConnection> connections = user.getReceivedConnections();

        List<User> userRequests = new ArrayList<>();

        for (UserConnection connection:
             connections) {
            if (connection.getStatus().equals(UserConnection.ConnectionStatus.REQUESTED)){
                userRequests.add(connection.getUser1());
            }
        }

        List<UserInfos> requests = userRequests.stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(requests);
    }

    @GetMapping("/status/{username}")
    private ResponseEntity<String> connectionStatus(@PathVariable String username){
        User user = userRepository.findByUsername(username).get();
        User currentUser = authService.getCurrentUser();

        Set<UserConnection> connections = currentUser.getReceivedConnections();
        for (UserConnection connection:
             connections) {
            if (connection.getUser1() == user || connection.getUser2() == user){
                if (connection.getStatus().equals(UserConnection.ConnectionStatus.CONNECTED)){
                    return ResponseEntity.status(HttpStatus.OK).body("CONNECTED");
                }
                else if(connection.getStatus().equals(UserConnection.ConnectionStatus.REQUESTED)){
                    return ResponseEntity.status(HttpStatus.OK).body("REQUESTED");
                }
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body("NotCONNECTED");

    }

    @PostMapping("/respond/{id}")
    private ResponseEntity<Void> respondToRequest(@PathVariable Long id,@RequestPart("response") String response){
        Set<UserConnection> connections = authService.getCurrentUser().getReceivedConnections();
        User user = userRepository.findById(id).get();
        for (UserConnection connection:
             connections) {
            if (connection.getUser2() == user){
                if (response.equals("ACCEPTED")){
                    connection.setStatus(UserConnection.ConnectionStatus.CONNECTED);
                }
                else {
                    connections.remove(connection);
                }
            }
        }
        authService.getCurrentUser().setReceivedConnections(connections);
        userRepository.save(authService.getCurrentUser());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    private ResponseEntity<List<UserInfos>> getAllConnections(){
        Set<UserConnection> connections = authService.getCurrentUser().getReceivedConnections();

        List<User> users = new ArrayList<>();

        for (UserConnection connection:
                connections) {
            if (connection.getStatus().equals(UserConnection.ConnectionStatus.CONNECTED)){
                users.add(connection.getUser1());
            }
        }

        List<UserInfos> requests = users.stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(requests);
    }
}
