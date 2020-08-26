package com.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.mail.Multipart;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileImageRequest {

    private String username;
    private Multipart image;
}
