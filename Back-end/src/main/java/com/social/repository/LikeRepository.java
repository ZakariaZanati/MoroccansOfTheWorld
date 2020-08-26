package com.social.repository;

import com.social.model.Like;
import com.social.model.Post;
import com.social.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {

    Optional<Like> findByPostAndUser(Post post, User user);
}
