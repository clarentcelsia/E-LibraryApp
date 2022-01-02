package com.project.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Post {
    @Id
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @GeneratedValue(generator = "uuid-generator")
    private String id;

    @Column(nullable = false)
    private String title;
    private String body;

    private Topic topic;

    private Date createdAt;
    private Date updatedAt;

}
