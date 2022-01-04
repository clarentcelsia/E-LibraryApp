package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reply")
public class Reply {
    @Id
    @SequenceGenerator(name = "sequence-generator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;


    @ManyToOne (targetEntity = Post.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
