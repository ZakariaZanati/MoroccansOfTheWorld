package com.social.service;

import com.social.exceptions.UserTypeException;
import com.social.model.Course;
import com.social.model.Provider;
import com.social.model.User;
import com.social.model.UserType;
import com.social.repository.CourseRepository;
import com.social.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

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
}
