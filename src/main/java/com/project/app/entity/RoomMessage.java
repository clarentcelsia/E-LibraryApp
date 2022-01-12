package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "room_message")
public class RoomMessage {
    @Id
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @GeneratedValue(generator = "uuid-generator")
    private String id;

    @ManyToOne(targetEntity = Room.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private Room room;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @Column(nullable = false)
    private String message;

    @Column(updatable = false)
    private Date createdAt;

    @PrePersist
    public void init(){
        if (createdAt == null) createdAt = new Date();
    }
}