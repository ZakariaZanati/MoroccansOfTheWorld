package com.social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDto {

    private String firstName;

    private String lastName;

    private String birthDate;

    private String phoneNumber;

    private String currentJob;

    private String website;

    private String aboutMe;

    private String country;

    private String city;
}
