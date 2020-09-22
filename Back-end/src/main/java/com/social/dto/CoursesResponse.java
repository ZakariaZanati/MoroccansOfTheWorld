package com.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursesResponse {

    private List<CourseResponse> groups;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
}
