package com.social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponse {

    private Long id;
    private String name;
    private String description;
    private String adminUserName;
    private byte[] imageBytes;

}
