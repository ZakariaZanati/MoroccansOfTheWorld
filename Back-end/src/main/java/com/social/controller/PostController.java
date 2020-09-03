package com.social.controller;

import com.social.dto.PostResponse;
import com.social.model.User;
import com.social.repository.PostRepository;
import com.social.repository.UserRepository;
import com.social.service.AuthService;
import com.social.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestPart(value = "postImage",required = false) MultipartFile file,
                                           @RequestPart("postDescription") String text) throws IOException {

        System.out.println("file intercepted");
        postService.save(file,text);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("user")
    public ResponseEntity<List<PostResponse>> getAllPostsByCurrentUser(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPostsByUser(authService.getCurrentUser()));
    }

    @GetMapping("user/{userName}")
    public ResponseEntity<List<PostResponse>> getAllPostsByUser(@PathVariable String userName){
        Optional<User> user = userRepository.findByUsername(userName);
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPostsByUser(user.get()));
    }

}
