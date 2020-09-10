package com.social.controller;

import com.social.dto.GroupResponse;
import com.social.model.Group;
import com.social.model.Status;
import com.social.model.User;
import com.social.repository.GroupRepository;
import com.social.repository.UserRepository;
import com.social.service.AuthService;
import com.social.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    @PostMapping
    public ResponseEntity<Void> createGroup(@RequestPart(value = "groupImage",required = false) MultipartFile file,
                                            @RequestPart("name") String name,
                                            @RequestPart("description") String description) throws IOException {

        groupService.save(file,name,description);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/request/{id}")
    public ResponseEntity.BodyBuilder sendRequest(@PathVariable("id") Long id){
        //System.out.println("send request");
        groupService.sendRequest(groupRepository.findById(id).get());
        return ResponseEntity.status(HttpStatus.OK);
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<List<User>> viewRequests(@PathVariable("id") Long id){
        Optional<Group> group = groupRepository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(groupService.viewRequests(group.get()));
    }

    @PostMapping("/respond/{id}")
    public ResponseEntity.BodyBuilder respondToRequest(@PathVariable("id") Long id,
                                                 @RequestPart("userName")String userName,
                                                 @RequestPart("response")String response){
        User user = userRepository.findByUsername(userName).get();

        if (response.equals("accepted")){
            groupService.respondToRequest(Status.JOINED,groupRepository.findById(id).get(),user);
        }
        else if (response.equals("rejected")){
            groupService.respondToRequest(Status.REJECTED,groupRepository.findById(id).get(),user);
        }

        return ResponseEntity.status(HttpStatus.OK);
    }

    @GetMapping("/request/status/{id}")
    public ResponseEntity<String> requestStatus(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(groupService.getStatus(groupRepository.findById(id).get()));
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getGroups(){
        return ResponseEntity.status(HttpStatus.OK).body(groupService.getAllGroups());
    }



    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> getGroup(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(groupService.getGroup(groupRepository.findById(id).get()));
    }
}
