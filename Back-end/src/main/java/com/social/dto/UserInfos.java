package com.social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfos {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private byte[] image;

}
