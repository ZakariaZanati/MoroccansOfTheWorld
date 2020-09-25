package com.social.repository;

import com.social.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;

public interface CourseRepository extends JpaRepository<Course,Long> {

    Page<Course> findByNameContains(String name, Pageable pageable);
    Page<Course> findByCategory(String name,Pageable pageable);
    Page<Course> findByDateTime(LocalDateTime date, Pageable pageable);
}
