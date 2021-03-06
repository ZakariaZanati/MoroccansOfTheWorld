package com.social.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String message;

    private Instant createdDate;

    private NotificationType notificationType;

    private boolean seen;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User user;
}
