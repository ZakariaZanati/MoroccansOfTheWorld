package com.social.service;

import com.social.dto.PostResponse;
import com.social.exceptions.SpringRedditException;
import com.social.mapper.PostMapper;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.Deflater;

@Service
@Slf4j
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostMapper postMapper;


    @Transactional
    public void save(MultipartFile file,String description) throws IOException {

        Post post;
        if (file != null){
            post = Post.builder()
                    .createdDate(Instant.now())
                    .description(description)
                    .likeCount(0)
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
                    .likeCount(0)
                    .user(this.getCurrentUser())
                    .build();
        }
        postRepository.save(post);
    }

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow(()->new SpringRedditException("User Not Found"));
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return  postRepository.findAll()
                    .stream()
                    .map(postMapper::mapToDto)
                    .collect(Collectors.toList());
    }


}
