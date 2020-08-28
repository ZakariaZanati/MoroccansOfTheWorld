package com.social.mapper;


import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.social.dto.PostResponse;
import com.social.model.Like;
import com.social.model.Post;
import com.social.model.User;
import com.social.repository.CommentRepository;
import com.social.repository.LikeRepository;
import com.social.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private AuthService authService;

    @Mapping(target = "id",source = "postId")
    @Mapping(target = "userName",source = "user.username")
    @Mapping(target = "commentCount",expression = "java(commentCount(post))")
    @Mapping(target = "likeCount",source = "likeCount")
    @Mapping(target = "duration",source = "createdDate")
    @Mapping(target = "liked",expression = "java(isLiked(post))")
    @Mapping(target = "image",source = "imageBytes")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post){
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post){
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isLiked(Post post){
        if (authService.isLoggedIn()){
            Optional<Like> like = likeRepository.findByPostAndUser(post,authService.getCurrentUser());
            return like.isPresent();
        }
        return false;
    }
}
