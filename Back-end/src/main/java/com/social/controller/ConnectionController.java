package com.social.controller;


import com.social.dto.UserInfos;
import com.social.mapper.UserMapper;
import com.social.model.User;
import com.social.model.UserConnection;
import com.social.repository.UserRepository;
import com.social.service.AuthService;
import com.social.service.ConnectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    private ConnectionsService connectionsService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/request/{id}")
    private ResponseEntity<Void> sendConnectionRequest(@PathVariable Long id){
        User user = authService.getCurrentUser();
        User requestedUser = userRepository.findById(id).get();
        UserConnection.ConnectionId connectionId = new UserConnection.ConnectionId(user.getUserId(),requestedUser.getUserId());
        UserConnection connection = UserConnection.builder()
                .connectionId(connectionId)
                .source(user)
                .target(requestedUser)
                .seen(false)
                .status(UserConnection.ConnectionStatus.REQUESTED)
                .build();
        System.out.println(connection.toString());
        Set<UserConnection> connections = new HashSet<>();
        connections.add(connection);
        System.out.println(connections);
        user.setConnections(connections);
        userRepository.save(user);
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
                userRequests.add(connection.getSource());
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

        Set<UserConnection> connections = currentUser.getConnections();
        connections.addAll(currentUser.getReceivedConnections());
        for (UserConnection connection:
             connections) {
            if (connection.getSource() == user || connection.getTarget() == user){
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
            if (connection.getSource() == user){
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
        user.setConnections(connections);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    private ResponseEntity<List<UserInfos>> getAllConnections(){

        User user = authService.getCurrentUser();

        return ResponseEntity.status(HttpStatus.OK).body(connectionsService.getConnections(user));
    }

    @GetMapping("/{id}")
    private ResponseEntity<List<UserInfos>> getUserConnections(@PathVariable Long id){
        User user = userRepository.findById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(connectionsService.getConnections(user));
    }

    @GetMapping("/pageable")
    private ResponseEntity<List<UserInfos>> getUsersPage(@Param(value = "page") int page,
                                                         @Param(value = "size") int size,
                                                         @Param(value = "name") String name,
                                                         @Param(value = "city")String city,
                                                         @Param(value = "country")String country){
        List<UserInfos> users = connectionsService.getUsers(page, size, name, city, country);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
