package com.social.service;


import com.social.exceptions.PostNotFoundException;
import com.social.model.*;
import com.social.repository.LikeRepository;
import com.social.repository.NotificationRepository;
import com.social.repository.PostRepository;
import com.social.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final AuthService authService;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Transactional
    public void like(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + postId));

        User user = authService.getCurrentUser();
        Optional<Like> like = likeRepository.findByPostAndUser(post,user);
        if (!like.isPresent()){
            post.setLikeCount(post.getLikeCount()+1);
            likeRepository.save(Like.builder().post(post).user(user).build());

            Notification notification = Notification.builder()
                    .user(post.getUser())
                    .message(user.getFirstName() + " "+user.getLastName() + " liked your post")
                    .createdDate(Instant.now())
                    .notificationType(NotificationType.LIKE)
                    .seen(false)
                    .build();
            notificationRepository.save(notification);
            userRepository.save(post.getUser());
        }
        else{
            post.setLikeCount(post.getLikeCount()-1);
            likeRepository.delete(like.get());
        }
        postRepository.save(post);

    }
}
