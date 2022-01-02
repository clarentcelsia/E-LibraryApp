package com.project.app.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "topic")
public class Topic {
    @Id
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @GeneratedValue(generator = "uuid-generator")
    private String id;

    @Column(nullable = false)
    private String topicSubject;

    @Column(nullable = false)
    private String subTopic;

    @Column(nullable = false)
    private String description;

    @ManyToOne (targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false,foreignKey = @ForeignKey(name = "fk_user_id"), updatable = false)
    private User user;

    @CreatedDate
    @Column(updatable = false)
    private Date createdAt;

    @PrePersist
    public void createdAt(){
        if (createdAt == null) this.createdAt = new Date();
    }

    public Topic(String topicSubject, String subTopic, String description, User user, Date createdAt) {
        this.topicSubject = topicSubject;
        this.subTopic = subTopic;
        this.description = description;
        this.user = user;
        this.createdAt = createdAt;
    }
}
