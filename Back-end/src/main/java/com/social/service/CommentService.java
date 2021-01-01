package com.social.service;

import com.social.dto.CommentsDto;
import com.social.exceptions.PostNotFoundException;
import com.social.mapper.CommentMapper;
import com.social.model.*;
import com.social.repository.CommentRepository;
import com.social.repository.PostRepository;
import com.social.repository.UserRepository;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    public void save(CommentsDto commentsDto){
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(()-> new PostNotFoundException(commentsDto.getPostId().toString()));

        User user = authService.getCurrentUser();
        Comment comment = commentMapper.map(commentsDto,post,user);
        commentRepository.save(comment);

        Notification notification = Notification.builder()
                .user(post.getUser())
                .message(user.getFirstName()+" "+user.getLastName()+" added a comment on your post")
                .notificationType(NotificationType.COMMENT)
                .createdDate(Instant.now())
                .seen(false)
                .build();
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(()->new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
