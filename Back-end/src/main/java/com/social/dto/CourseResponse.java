package com.social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponse {

    private Long id;

    private String name;

    private String description;

    private String dateTime;

    private String time;

    private String duration;

    private String location;

    private String link;

    private String providerUsername;

    private String category;

    private byte[] imageBytes;
}
