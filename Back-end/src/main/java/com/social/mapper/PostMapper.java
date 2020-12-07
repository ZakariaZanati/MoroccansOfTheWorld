package com.social.mapper;


import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.social.controller.ProfileController;
import com.social.dto.PostResponse;
import com.social.model.Like;
import com.social.model.Post;
import com.social.model.User;
import com.social.repository.CommentRepository;
import com.social.repository.LikeRepository;
import com.social.repository.UserRepository;
import com.social.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private AuthService authService;

    @Mapping(target = "id",source = "postId")
    @Mapping(target = "userName",source = "user.username")
    @Mapping(target = "firstName",source = "user.firstName")
    @Mapping(target = "lastName",source = "user.lastName")
    @Mapping(target = "verifiedUser",source = "user.verified")
    @Mapping(target = "profileImage", expression = "java(getImage(post.getUser().getUsername()))")
    @Mapping(target = "commentCount",expression = "java(commentCount(post))")
    @Mapping(target = "likeCount",source = "likeCount")
    @Mapping(target = "duration",expression = "java(getDuration(post))")
    @Mapping(target = "liked",expression = "java(isLiked(post))")
    @Mapping(target = "image",source = "imageBytes")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post){
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post){

        LocalDateTime toDateTime = LocalDateTime.now();
        LocalDateTime fromDateTime = LocalDateTime.ofInstant(post.getCreatedDate(), ZoneId.systemDefault());

        LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);
        long year = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears(year);

        long months = tempDateTime.until( toDateTime, ChronoUnit.MONTHS );
        tempDateTime = tempDateTime.plusMonths( months );

        long days = tempDateTime.until( toDateTime, ChronoUnit.DAYS );
        tempDateTime = tempDateTime.plusDays( days );


        long hours = tempDateTime.until( toDateTime, ChronoUnit.HOURS );
        tempDateTime = tempDateTime.plusHours( hours );

        long minutes = tempDateTime.until( toDateTime, ChronoUnit.MINUTES );



        String duration = "";

        if (year != 0){
            duration += fromDateTime.getDayOfMonth()+" "+fromDateTime.getMonth().toString().toLowerCase() + " " + fromDateTime.getYear();
        } else if (months != 0 || days != 0){
            duration = fromDateTime.getDayOfMonth()+" "+fromDateTime.getMonth().toString().toLowerCase();
        } else if (hours != 0){
            duration = (hours == 1 ? hours+" hr" : (hours + " hrs"));
        } else if (minutes != 0){
            duration = (minutes == 1 ? minutes+" min" : (minutes + " mins"));
        } else {
            duration = "just now";
        }

        return duration;
    }

    boolean isLiked(Post post){
        if (authService.isLoggedIn()){
            Optional<Like> like = likeRepository.findByPostAndUser(post,authService.getCurrentUser());
            return like.isPresent();
        }
        return false;
    }

    byte[] getImage(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User name not found - " + username));;
        if (user.getImage() == null)
            return null;
        return ProfileController.decompressBytes(user.getImage().getPicByte());
    }
}
