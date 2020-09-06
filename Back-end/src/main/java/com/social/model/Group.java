package com.social.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "groupe")
public class Group {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotBlank(message = "Group name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    private String adminUserName;
    @OneToMany(fetch = LAZY)
    private List<Post> posts;
    private Instant createdDate;
    @Nullable
    @Column(length = 1048570)
    private byte[] imageBytes;
    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private Set<User> users;

    @ManyToOne
    @JoinColumn(name = "providerId",referencedColumnName = "userId")
    private Provider provider;
}
