package com.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOfferDto {

    private Long id;
    private String post;
    private String enterprise;
    private String location;
    private String contractType;
    private String description;
    private String qualifications;
    private String salary;
    private String creationDate;
    private String expirationDate;
    private String author;
    private String link;

}
