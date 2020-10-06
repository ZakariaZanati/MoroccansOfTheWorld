package com.social.repository;

import com.social.model.Group;
import com.social.model.User;
import com.social.model.UserGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAllByVerificationRequestedAndVerified(Boolean verification,Boolean verified);
    Page<User> findByFirstNameContainsOrLastNameContains(String firstName, String lastName, Pageable pageable);
    Page<User> findByCityOrCountry(String city,String country,Pageable pageable);
}
