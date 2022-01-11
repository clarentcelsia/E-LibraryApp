package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reply")
public class Reply {
    @Id
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @GeneratedValue(generator = "uuid-generator")
    private String id;


    @ManyToOne (targetEntity = Post.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, updatable = false)
    @JsonBackReference
    private Post post;

    @ManyToOne (targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column ( nullable = false)
    private String message;

    @CreatedDate
    @Column(updatable = false)
    private Date createdAt;

    @PrePersist
    public void createDate(){
        if ( createdAt == null) createdAt = new Date();
    }
}
