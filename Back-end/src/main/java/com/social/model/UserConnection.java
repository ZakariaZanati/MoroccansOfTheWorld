package com.social.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserConnection implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn
    private User user1;

    @Id
    @ManyToOne
    @JoinColumn
    private User user2;

    private ConnectionStatus status;

    private boolean seen;

    public enum ConnectionStatus{
        REQUESTED,
        CONNECTED
    }

    @Override
    public String toString() {
        return "UserConnection{" +
                "user1=" + user1 +
                ", user2=" + user2 +
                ", status=" + status +
                '}';
    }
}
