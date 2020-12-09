package com.social.service;

import com.social.dto.GroupResponse;
import com.social.dto.GroupsResponse;
import com.social.dto.UserInfos;
import com.social.exceptions.UserTypeException;
import com.social.mapper.GroupMapper;
import com.social.mapper.UserMapper;
import com.social.model.*;
import com.social.repository.GroupRepository;
import com.social.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public void save(MultipartFile file,String name,String description) throws IOException {
        User user = authService.getCurrentUser();
        if (user.getUserType().equals(UserType.PROVIDER)){
            Group group;
            if (file != null){
                group = Group.builder()
                        .adminUserName(user.getUsername())
                        .createdDate(Instant.now())
                        .name(name)
                        .description(description)
                        .imageBytes(file.getBytes())
                        .provider((Provider) user)
                        .build();
            }
            else{
                group = Group.builder()
                        .adminUserName(user.getUsername())
                        .createdDate(Instant.now())
                        .name(name)
                        .description(description)
                        .provider((Provider) user)
                        .build();
            }

            groupRepository.save(group);
        }
        else throw new UserTypeException("Operation not allowed for this type of user");
    }

    public void sendRequest(Group group){
        User user = authService.getCurrentUser();
        UserGroup userGroup = new UserGroup(group,user,Status.REQUESTED,Instant.now());
        System.out.println(userGroup.toString());
        //userGroupRepository.save(userGroup);
        Set<UserGroup> userGroups = new HashSet<>();
        userGroups.add(userGroup);
        group.setUserGroups(userGroups);
    }

    public List<UserInfos> viewRequests(Group group){
        Set<UserGroup> userGroups = group.getUserGroups();
        System.out.println(userGroups);
        List<User> users = new ArrayList<>();
        for (UserGroup usergroup:
             userGroups) {
            if (usergroup.getStatus().equals(Status.REQUESTED)){
                users.add(usergroup.getUser());
            }
        }

        return users.stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }
    /*
    public GroupsResponse getUserGroups(){
        User user = authService.getCurrentUser();
        Set<UserGroup> userGroups = user.getUserGroups();
        List<Group> groups = new ArrayList<>();
        for (UserGroup usergroup:
             userGroups) {
            if (usergroup.getStatus().equals(Status.JOINED)){
                groups.add(usergroup.getGroup());
            }
        }
        List<GroupResponse> groupResponses = groups.stream()
                .map(groupMapper::mapToDto)
                .collect(Collectors.toList());

        GroupsResponse groupsResponse = new GroupsResponse()
    }

     */

    public void respondToRequest(Status status,Group group,User user){
        Set<UserGroup> userGroups = user.getUserGroups();
        for (UserGroup usergroup:
             userGroups) {
            if (usergroup.getGroup().equals(group)){
                usergroup.setStatus(status);
            }
        }
        user.setUserGroups(userGroups);
        userRepository.save(user);
    }

    public String getStatus(Group group){
        Set<UserGroup> userGroups = authService.getCurrentUser().getUserGroups();
        for (UserGroup usergroup:
                userGroups) {
            if (usergroup.getGroup().equals(group)){
                return usergroup.getStatus().name();
            }
        }
        return "NotRequested";
    }

    public List<GroupResponse> getAllGroups(){
        return groupRepository.findAll()
                .stream()
                .map(groupMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<GroupResponse> preview(){
        Pageable pageable = PageRequest.of(0,4);
        Page<Group> page = groupRepository.findAll(pageable);
        return page.stream()
                .map(groupMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<UserInfos> getGroupMembers(Group group){
        Set<UserGroup> userGroups = group.getUserGroups();
        Set<User> users = userRepository.findByUserGroupsIn(userGroups);
        return users.stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public GroupResponse getGroup(Group group){
        return GroupResponse.builder()
                .id(group.getId())
                .description(group.getDescription())
                .name(group.getName())
                .adminUserName(group.getAdminUserName())
                .imageBytes(group.getImageBytes())
                .build();

    }


}
