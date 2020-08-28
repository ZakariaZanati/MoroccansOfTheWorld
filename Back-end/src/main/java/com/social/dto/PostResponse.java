package com.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private Long id;
    private String description;
    private String userName;
    private Integer likeCount;
    private Integer commentCount;
    private String duration;
    private boolean liked;
    private byte[] image;

}
