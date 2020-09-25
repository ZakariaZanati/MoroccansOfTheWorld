package com.social.mapper;

import com.social.dto.CourseResponse;
import com.social.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "id",source = "id")
    @Mapping(target = "name",source = "name")
    @Mapping(target = "description",source = "description")
    @Mapping(target = "providerUsername",source = "providerUsername")
    @Mapping(target = "imageBytes",source = "imageBytes")
    @Mapping(target = "category",source = "category")
    @Mapping(target = "link",source = "link")
    @Mapping(target = "location",source = "location")
    @Mapping(target = "dateTime",expression = "java(course.getDateTime().toString())")
    @Mapping(target = "duration",source = "duration")
    @Mapping(target = "time",source = "time")
    CourseResponse mapToDto(Course course);
}
