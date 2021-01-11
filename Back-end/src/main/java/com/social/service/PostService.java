package com.social.service;

import com.social.dto.PostResponse;
import com.social.exceptions.PostNotFoundException;
import com.social.exceptions.SpringException;
import com.social.mapper.PostMapper;
import com.social.model.Group;
import com.social.model.Post;
import com.social.model.User;
import com.social.repository.GroupRepository;
import com.social.repository.PostRepository;
import com.social.repository.UserRepository;
import lombok.AllArgsConstructor;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.Deflater;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final GroupRepository groupRepository;

    private final PostMapper postMapper;

    private final AuthService authService;


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
                    .user(authService.getCurrentUser())
                    .build();
        }
        else{
            post = Post.builder()
                    .createdDate(Instant.now())
                    .description(description)
                    .likeCount(0)
                    .user(authService.getCurrentUser())
                    .build();
        }
        postRepository.save(post);
    }

    @Transactional
    public void saveToGroup(MultipartFile file,String description,Long id) throws IOException {
        Post post;
        Optional<Group> group = groupRepository.findById(id);

        if (file != null){
            post = Post.builder()
                    .createdDate(Instant.now())
                    .description(description)
                    .likeCount(0)
                    .imageName(file.getOriginalFilename())
                    .imageType(file.getContentType())
                    .imageBytes(file.getBytes())
                    .user(authService.getCurrentUser())
                    .group(group.get())
                    .build();
        }
        else{
            post = Post.builder()
                    .createdDate(Instant.now())
                    .description(description)
                    .likeCount(0)
                    .user(authService.getCurrentUser())
                    .group(group.get())
                    .build();
        }
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return  postRepository.findAllByGroupIsNullOrderByCreatedDateDesc()
                    .stream()
                    .map(postMapper::mapToDto)
                    .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPostsByUser(User user){
        return postRepository.findByUserOrderByCreatedDateDesc(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPostsByGroup(Group group){
        return postRepository.findAllByGroup(group)
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }
}
