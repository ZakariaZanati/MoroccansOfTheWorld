package com.social.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long postId;
    @Nullable
    @Lob
    private String description;
    private Integer likeCount = 0;
    private Integer commentCount = 0;

    private Instant createdDate;

    @Nullable
    private String imageName;
    @Nullable
    private String imageType;
    @Nullable
    @Column(length = 1048570)
    private byte[] imageBytes;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "groupId", referencedColumnName = "id")
    private Group group;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

}
