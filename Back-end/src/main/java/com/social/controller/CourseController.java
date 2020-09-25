package com.social.controller;


import com.social.dto.CourseResponse;
import com.social.dto.CoursesResponse;
import com.social.model.Course;
import com.social.model.Group;
import com.social.repository.CourseRepository;
import com.social.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Void> createCourse(@RequestPart("courseImg") MultipartFile courseImg,
                                             @RequestPart("name") String name,
                                             @RequestPart("description")String description,
                                             @RequestPart("location")  String location,
                                             @RequestPart("link")String link,
                                             @RequestPart("category") String category,
                                             @RequestPart("date")String date,
                                             @RequestPart(value = "duration",required = false) String duration,
                                             @RequestPart("time") String time) throws IOException, ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDateTime dateTime = LocalDateTime.parse(date,formatter);
        System.out.println(dateTime);
        courseService.save(courseImg, name, description, dateTime, location, link, category, duration,time);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CoursesResponse> getCourses(@Param(value = "page") int page,
                                                      @Param(value = "size") int size,
                                                      @Param(value = "name") String name,
                                                      @Param(value = "category")String category,
                                                      @Param(value = "date")String date){
        Pageable requestedPage = PageRequest.of(page,size);
        CoursesResponse coursesResponse = courseService.getCourses(requestedPage,name,category);
        System.out.println(coursesResponse);
        return ResponseEntity.status(HttpStatus.OK).body(coursesResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(courseRepository.findById(id).get());
    }
}
