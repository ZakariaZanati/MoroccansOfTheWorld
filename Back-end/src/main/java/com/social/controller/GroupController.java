package com.social.controller;

import com.social.dto.GroupResponse;
import com.social.dto.GroupsResponse;
import com.social.dto.UserInfos;
import com.social.mapper.GroupMapper;
import com.social.model.Group;
import com.social.model.Status;
import com.social.model.User;
import com.social.model.UserGroup;
import com.social.repository.GroupRepository;
import com.social.repository.UserRepository;
import com.social.service.AuthService;
import com.social.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupMapper groupMapper;

    @PostMapping
    public ResponseEntity<Void> createGroup(@RequestPart(value = "groupImage",required = false) MultipartFile file,
                                            @RequestPart("name") String name,
                                            @RequestPart("description") String description) throws IOException {
        System.out.println(name + " " + description);

        groupService.save(file,name,description);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/request/{id}")
    public ResponseEntity.BodyBuilder sendRequest(@PathVariable("id") Long id){
        //System.out.println("send request");
        groupService.sendRequest(groupRepository.findById(id).get());
        return ResponseEntity.status(HttpStatus.OK);
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<List<UserInfos>> viewRequests(@PathVariable("id") Long id){
        Optional<Group> group = groupRepository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(groupService.viewRequests(group.get()));
    }

    @PostMapping("/respond/{id}")
    public ResponseEntity<Void> respondToRequest(@PathVariable("id") Long id,
                                                 @RequestPart("userName")String userName,
                                                 @RequestPart("response")String response){
        User user = userRepository.findByUsername(userName).get();

        if (response.equals("accepted")){
            groupService.respondToRequest(Status.JOINED,groupRepository.findById(id).get(),user);
        }
        else if (response.equals("rejected")){
            groupService.respondToRequest(Status.REJECTED,groupRepository.findById(id).get(),user);
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/request/status/{id}")
    public ResponseEntity<String> requestStatus(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(groupService.getStatus(groupRepository.findById(id).get()));
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getGroups(){
        return ResponseEntity.status(HttpStatus.OK).body(groupService.getAllGroups());
    }

    @GetMapping("/pageable")
    public ResponseEntity<GroupsResponse> getGroupsPage(@Param(value = "page") int page,
                                                        @Param(value = "size") int size,
                                                        @Param(value = "name") String name,
                                                        @Param(value = "current") Boolean current){
        System.out.println(current);
        Pageable requestedPage = PageRequest.of(page,size);
        Page<Group> groups;
        if (!current){
            if (name.equals("")){
                groups = groupRepository.findAll(requestedPage);
            }
            else{
                groups = groupRepository.findByNameContains(name,requestedPage);
            }
        }
        else{
            User user = authService.getCurrentUser();
            Set<UserGroup> userGroups = user.getUserGroups();
            userGroups.removeIf(usergroup -> !usergroup.getStatus().equals(Status.JOINED));
            groups = groupRepository.findByUserGroupsIn(userGroups,requestedPage);
        }

        List<GroupResponse> groupResponses = groups.stream()
                .map(groupMapper::mapToDto)
                .collect(Collectors.toList());
        GroupsResponse groupsResponse = new GroupsResponse(groupResponses,groups.getTotalPages(),groups.getNumber(),groups.getSize());
        return ResponseEntity.status(HttpStatus.OK).body(groupsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> getGroup(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(groupService.getGroup(groupRepository.findById(id).get()));
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<List<UserInfos>> getGroupMembers(@PathVariable("id") Long id){
        Group group = groupRepository.findById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(groupService.getGroupMembers(group));
    }
}
