package com.social.repository;

import com.social.model.Group;
import com.social.model.Post;
import com.social.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByGroup(Group group);

    List<Post> findByUserOrderByCreatedDateDesc(User user);

    List<Post> findAllByOrderByCreatedDateDesc();

    List<Post> findAllByGroupIsNullOrderByCreatedDateDesc();

}
