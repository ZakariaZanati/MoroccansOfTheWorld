package com.social.model;


import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;


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
    @Lob
    @Nullable
    //@Column(length = 1048570)
    private byte[] imageBytes;

    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL)
    private Set<UserGroup> userGroups;

    @ManyToOne
    @JoinColumn(name = "providerId",referencedColumnName = "userId")
    private Provider provider;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Nullable
    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(@Nullable byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public Set<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
