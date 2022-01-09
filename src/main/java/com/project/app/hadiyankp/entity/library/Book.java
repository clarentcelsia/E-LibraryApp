package com.project.app.hadiyankp.entity.library;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mst_book")
public class Book {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String cover;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String publisher;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date publishDate;

    @Column(nullable = false)
    private String book;

    private String total_ratings;

    private String total_review;

    private String stock;

    @ManyToMany(targetEntity = Category.class,fetch = FetchType.LAZY,cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(targetEntity = Type.class,fetch = FetchType.LAZY,cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "type_id")
    private Type type;


    @CreatedDate
    @Column(updatable = false)
    private Date createdAt;

    @LastModifiedDate
    private Date updateAt;

    @PrePersist //pre PrePersist akan dijalankan ketika insert data dilakukan
    private void createDate() {
        if (this.createdAt == null) this.createdAt = new Date();
        if (this.updateAt == null) this.updateAt = new Date();
    }

    @PreUpdate //pre preUpdate akan dijalankan ketika update data dilakukan
    private void updatedDate() {
        this.updateAt = new Date();
    }

    public Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getTotal_ratings() {
        return total_ratings;
    }

    public void setTotal_ratings(String total_ratings) {
        this.total_ratings = total_ratings;
    }

    public String getTotal_review() {
        return total_review;
    }

    public void setTotal_review(String total_review) {
        this.total_review = total_review;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Book(String id, String cover, String title, String description, String publisher, Date publishDate, String book, String total_ratings, String total_review, String stock, Category category, Type type, Date createdAt, Date updateAt) {
        this.id = id;
        this.cover = cover;
        this.title = title;
        this.description = description;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.book = book;
        this.total_ratings = total_ratings;
        this.total_review = total_review;
        this.stock = stock;
        this.category = category;
        this.type = type;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
}