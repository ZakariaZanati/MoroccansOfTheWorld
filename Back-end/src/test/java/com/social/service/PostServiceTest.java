package com.social.service;

import com.social.dto.PostResponse;
import com.social.mapper.PostMapper;
import com.social.model.Post;
import com.social.model.User;
import com.social.repository.GroupRepository;
import com.social.repository.PostRepository;
import com.social.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private PostMapper postMapper;
    @Mock
    private AuthService authService;

    PostService postService;

    @BeforeEach
    public void setup(){
        postService = new PostService(postRepository,userRepository,groupRepository,postMapper,authService);
    }

    @Test
    @DisplayName("Should Find Post By Id")
    void ShouldFindPostById() {
        Post post = new Post(123L,"This is a post",0,0, Instant.now(),null,null,null,null,null);
        PostResponse expectedPostResponse = new PostResponse(123L,"This is a post","zakaria",0,0,"2 min ago","Zakaria","Zanati",true,null,false,null);

        Mockito.when(postRepository.findById(123L)).thenReturn(Optional.of(post));
        Mockito.when(postMapper.mapToDto(Mockito.any(Post.class))).thenReturn(expectedPostResponse);

        PostResponse actualPostResponse = postService.getPost(123L);

        Assertions.assertThat(actualPostResponse.getId()).isEqualTo(expectedPostResponse.getId());
        Assertions.assertThat(actualPostResponse.getDescription()).isEqualTo(expectedPostResponse.getDescription());
    }

    @Test
    @DisplayName("Should save post")
    void ShouldSavePost() throws IOException {

        User currentUser = new User(1L,"zakaria","password","zakaria@gmail.com",1,"zakaria","zanati");

        Mockito.when(authService.getCurrentUser()).thenReturn(currentUser);

        postService.save(null,"this is a post");
        Mockito.verify(postRepository,Mockito.times(1)).save(ArgumentMatchers.any(Post.class));


    }
}