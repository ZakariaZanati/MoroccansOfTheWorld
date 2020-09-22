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
    @Mapping(target = "date",source = "date")
    @Mapping(target = "duration",source = "duration")
    CourseResponse mapToDto(Course course);
}
