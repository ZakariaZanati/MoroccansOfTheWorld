package com.social.service;

import com.social.exceptions.SpringRedditException;
import com.social.model.Post;
import com.social.model.User;
import com.social.repository.PostRepository;
import com.social.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;

@Service
@Slf4j
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void save(MultipartFile file,String description) throws IOException {

        Post post;
        if (file != null){
            post = Post.builder()
                    .createdDate(Instant.now())
                    .description(description)
                    .imageName(file.getOriginalFilename())
                    .imageType(file.getContentType())
                    .imageBytes(file.getBytes())
                    .user(this.getCurrentUser())
                    .build();
        }
        else{
            post = Post.builder()
                    .createdDate(Instant.now())
                    .description(description)
                    .user(this.getCurrentUser())
                    .build();
        }
        postRepository.save(post);
    }

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow(()->new SpringRedditException("User Not Found"));
    }
}
