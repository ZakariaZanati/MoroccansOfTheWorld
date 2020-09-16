package com.social.repository;

import com.social.model.Group;
import com.social.model.UserGroup;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GroupRepository extends PagingAndSortingRepository<Group,Long> {

    List<Group> findAll();

    Optional<Group> findById(Long id);

    Page<Group> findByNameContains(String name,Pageable pageable);
    Page<Group> findByUserGroupsIn(java.util.Set userGroup, Pageable pageable);

}
