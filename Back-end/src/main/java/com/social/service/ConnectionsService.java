package com.social.service;

import com.social.dto.UserInfos;
import com.social.dto.UserResponse;
import com.social.dto.UsersResponse;
import com.social.mapper.ConnectionMapper;
import com.social.mapper.UserMapper;
import com.social.model.User;
import com.social.model.UserConnection;
import com.social.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConnectionsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ConnectionMapper connectionMapper;

    @Transactional
    public List<UserInfos> getConnections(User user){
        Set<UserConnection> connections = user.getConnections();
        Set<UserConnection> receivedConnections = user.getReceivedConnections();
        List<User> users = new ArrayList<>();

        for (UserConnection connection:
                connections) {
            if (connection.getStatus().equals(UserConnection.ConnectionStatus.CONNECTED)){
                users.add(connection.getTarget());
            }
        }
        for (UserConnection connection:
                receivedConnections) {
            if (connection.getStatus().equals(UserConnection.ConnectionStatus.CONNECTED)){
                users.add(connection.getSource());
            }
        }

        return users.stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UsersResponse getUsers(int page, int size, String name, String city, String country){

        Pageable requestedPage = PageRequest.of(page,size);
        Page<User> users;

        if (name.equals("")){
            if (!city.equals("") || !country.equals("")){
                users = userRepository.findByCityOrCountry(city,country,requestedPage);
            }
            else {
                users = userRepository.findAll(requestedPage);
            }
        }
        else {
            users = userRepository.findByFirstNameContainsOrLastNameContains(name,name,requestedPage);
        }

        List<UserResponse> userResponses = users.stream()
                .map(connectionMapper::mapToDto)
                .collect(Collectors.toList());

        return new UsersResponse(userResponses,users.getTotalPages(),users.getNumber(),users.getSize());
    }
}
