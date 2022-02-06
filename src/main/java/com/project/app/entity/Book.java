package com.project.app.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.app.entity.library.Category;
import com.project.app.entity.library.Type;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "mst_book")
public class Book {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String bookCode;

    private String cover;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String publisher;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishedDate;

    private String bookUrl;

    private Float totalRating;

    private Integer totalReview;

    private Integer stock;

    @ManyToOne(targetEntity = Category.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(targetEntity = Type.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private Type type;

//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade =
//                    {
//                            CascadeType.MERGE,
//                            CascadeType.PERSIST
//                    })
//    @JoinTable(name = "tb_book_author",
//            joinColumns = @JoinColumn(name = "author_id",
//                    nullable = false,
//                    updatable = false),
//            inverseJoinColumns = @JoinColumn(name = "book_id",
//                    nullable = false,
//                    updatable = false))
//    private List<Author> authors = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private Date createdAt;

    @LastModifiedDate
    private Date updateAt;

    @PrePersist //pre PrePersist akan dijalankan ketika insert data dilakukan
    private void createDate() {
        if (this.createdAt == null) this.createdAt = new Date();
        if (this.updateAt == null) this.updateAt = new Date();
        if (this.stock == null) this.stock = 1;
        if (this.totalRating == null) this.totalRating = 0f;
        if (this.totalReview == null) this.totalReview = 0;
    }

    @PreUpdate //pre preUpdate akan dijalankan ketika update data dilakukan
    private void updatedDate() {
        this.updateAt = new Date();
    }
}
