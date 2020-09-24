package com.social.service;

import com.social.dto.CourseResponse;
import com.social.dto.CoursesResponse;
import com.social.exceptions.UserTypeException;
import com.social.mapper.CourseMapper;
import com.social.model.Course;
import com.social.model.Provider;
import com.social.model.User;
import com.social.model.UserType;
import com.social.repository.CourseRepository;
import com.social.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseMapper courseMapper;

    public void save(MultipartFile file, String name,
                     String description,
                     Date date,
                     String location,
                     String link,
                     String category,
                     String duration) throws IOException {
        User user = authService.getCurrentUser();
        if (user.getUserType().equals(UserType.PROVIDER)){
            Course course = Course.builder()
                    .category(category)
                    .date(date)
                    .description(description)
                    .duration(duration)
                    .imageBytes(file.getBytes())
                    .link(link)
                    .location(location)
                    .name(name)
                    .providerUsername(user.getUsername())
                    .provider((Provider) user)
                    .build();
            courseRepository.save(course);
        }
        else throw new UserTypeException("Operation not allowed for this type of user");

    }

    public CoursesResponse getCourses(Pageable pageable,String name,String category){

        Page<Course> courses;
        if (name.equals("")){
            if (category.equals("")){
                courses = courseRepository.findAll(pageable);
            }
            else {
                courses = courseRepository.findByCategory(category,pageable);
            }
        }
        else {
            courses = courseRepository.findByNameContains(name,pageable);
        }

        List<CourseResponse> courseResponses = courses.stream()
                .map(courseMapper::mapToDto)
                .collect(Collectors.toList());

        CoursesResponse coursesResponse = new CoursesResponse(courseResponses,courses.getTotalPages(),courses.getNumber(),courses.getSize());;
        return coursesResponse;
    }
}
