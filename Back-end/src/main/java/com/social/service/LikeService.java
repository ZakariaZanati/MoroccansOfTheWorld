package com.social.service;


import com.social.exceptions.PostNotFoundException;
import com.social.model.Like;
import com.social.model.Post;
import com.social.repository.LikeRepository;
import com.social.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final AuthService authService;

    @Transactional
    public void like(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + postId));
        Optional<Like> like = likeRepository.findByPostAndUser(post,authService.getCurrentUser());
        if (!like.isPresent()){
            post.setLikeCount(post.getLikeCount()+1);
            likeRepository.save(Like.builder().post(post).user(authService.getCurrentUser()).build());
        }
        else{
            post.setLikeCount(post.getLikeCount()-1);
            likeRepository.delete(Like.builder().post(post).user(authService.getCurrentUser()).build());
        }
        postRepository.save(post);

    }
}
