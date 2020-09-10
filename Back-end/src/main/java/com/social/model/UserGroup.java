package com.social.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserGroup implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn
    private Group group;

    @Id
    @ManyToOne
    @JoinColumn
    private User user;


    private Status status;
    private Instant registeredDate;

    @Override
    public String toString() {
        return "UserGroup{" +
                "group=" + group +
                ", user=" + user +
                ", status=" + status +
                ", registeredDate=" + registeredDate +
                '}';
    }
}
