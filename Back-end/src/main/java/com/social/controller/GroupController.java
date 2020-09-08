package com.social.controller;

import com.social.model.Group;
import com.social.repository.GroupRepository;
import com.social.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupRepository groupRepository;

    @PostMapping
    public ResponseEntity<Void> createGroup(@RequestPart(value = "groupImage",required = false) MultipartFile file,
                                            @RequestPart("name") String name,
                                            @RequestPart("description") String description) throws IOException {

        groupService.save(file,name,description);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Group>> getGroups(){
        return ResponseEntity.status(HttpStatus.OK).body(groupRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroup(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(groupRepository.findById(id).get());
    }
}
