package com.social.repository;

import com.social.model.Group;
import com.social.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GroupRepository extends JpaRepository<Group,Long> {

    List<Group> findAll();

    List<Group> findAllByUsersIn(Collection<Set<User>> users);

    Optional<Group> findById(Long id);
}
