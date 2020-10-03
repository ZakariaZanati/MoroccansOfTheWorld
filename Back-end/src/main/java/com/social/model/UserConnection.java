package com.social.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserConnection implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ConnectionId connectionId;

    @ManyToOne
    @JoinColumn(name = "source_id",insertable = false,updatable = false)
    private User source;


    @ManyToOne
    @JoinColumn(name = "target_id",insertable = false,updatable = false)
    private User target;

    @Column
    private ConnectionStatus status;

    @Column
    private boolean seen;

    public enum ConnectionStatus{
        REQUESTED,
        CONNECTED
    }

    @Override
    public String toString() {
        return "UserConnection{" +
                "user1=" + source +
                ", user2=" + target +
                ", status=" + status +
                '}';
    }

    @Embeddable
    @NoArgsConstructor
    public static class ConnectionId implements Serializable{
        @Column(name="source_id", nullable=false, updatable=false)
        private Long sourceId;

        @Column(name="target_id", nullable=false, updatable=false)
        private Long targetId;

        public ConnectionId(Long sourceId,Long targetId) {
            this.sourceId = sourceId;
            this.targetId = targetId;
        }

        public Long getSourceId() {
            return sourceId;
        }

        public void setSourceId(Long sourceId) {
            this.sourceId = sourceId;
        }

        public Long getTargetId() {
            return targetId;
        }

        public void setTargetId(Long targetId) {
            this.targetId = targetId;
        }
    }
}
