package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @GeneratedValue(generator = "uuid-generator")
    private String id;

    private String roomKey;

    @Column (nullable = false)
    private String topic;

    private String subTopic;

    @Column (updatable = false)
    private Date createdAt;

    @ManyToOne ( targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany ( targetEntity = RoomMember.class, orphanRemoval = true, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Set<RoomMember> roomMember;

    @OneToMany ( targetEntity = RoomMessage.class, orphanRemoval = true, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<RoomMessage> roomMessage;

    @PrePersist
    public void init(){
        if (createdAt == null) createdAt = new Date();
    }


}
