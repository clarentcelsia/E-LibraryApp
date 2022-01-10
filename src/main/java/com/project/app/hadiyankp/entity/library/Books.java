package com.project.app.hadiyankp.entity.library;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "mst_book")
public class Books {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String cover;

    @Column(nullable = false)
    private String title;

    private String description;

    @ManyToMany(
            fetch = FetchType.EAGER, cascade = {
            CascadeType.ALL
    }, mappedBy = "books")
    @JsonBackReference
    private List<Author> authors = new ArrayList<>();

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

    public Books() {
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

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public Float getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Float totalRating) {
        this.totalRating = totalRating;
    }

    public Integer getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(Integer totalReview) {
        this.totalReview = totalReview;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Books{" +
                "id='" + id + '\'' +
                ", cover='" + cover + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", authors=" + authors +
                ", publisher='" + publisher + '\'' +
                ", publishDate=" + publishedDate +
                ", book='" + bookUrl + '\'' +
                ", total_ratings=" + totalRating +
                ", total_review=" + totalReview +
                ", stock=" + stock +
                ", category=" + category +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", updateAt=" + updateAt +
                '}';
    }
}