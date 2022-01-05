package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "room_member")
public class RoomMember {
    @Id
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @GeneratedValue(generator = "uuid-generator")
    private String id;

    @ManyToOne(targetEntity = Room.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private Room room;

    @ManyToOne ( targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Column (updatable = false)
    private Date createdAt;

    @PrePersist
    public void init(){
        if (createdAt == null) createdAt = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomMember that = (RoomMember) o;
        return Objects.equals(room, that.room) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, user);
    }

}
