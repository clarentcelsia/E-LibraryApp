package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "mst_ebook")
public class Ebook {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String ebookId;
    private String ebookCode;
    private String title;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JoinTable(
            name = "tb_ebook_authors",
            joinColumns = @JoinColumn(name = "ebook_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @JsonManagedReference
    private List<EbookAuthor> authors = new ArrayList<>();

    private String publishedDate;
    private String publisher;
    private String description;
    private String imageLinks;
    private String webReaderLink;

    @CreatedDate
    @Column(updatable = false)
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;

    private Boolean isDeleted;

    @PrePersist
    private void prePersist() {
        if (this.createdAt == null) this.createdAt = new Date();
        if (this.updatedAt == null) this.updatedAt = new Date();
        if (this.isDeleted == null) isDeleted = false;
        authors = new ArrayList<>();
    }

    @PreUpdate
    private void preUpdate(){
        this.updatedAt = new Date();
    }

    public Ebook() {
    }

    public Ebook(String ebookCode, String title, String publishedDate, String publisher, String description, String imageLinks, String webReaderLink) {
        this.ebookCode = ebookCode;
        this.title = title;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
        this.description = description;
        this.imageLinks = imageLinks;
        this.webReaderLink = webReaderLink;
    }

    public Ebook(String ebookCode, String title, List<EbookAuthor> authors, String publishedDate, String publisher, String description, String imageLinks, String webReaderLink) {
        this.ebookCode = ebookCode;
        this.title = title;
        this.authors = authors;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
        this.description = description;
        this.imageLinks = imageLinks;
        this.webReaderLink = webReaderLink;
    }


    public String getEbookId() {
        return ebookId;
    }

    public void setEbookId(String ebookId) {
        this.ebookId = ebookId;
    }

    public String getEbookCode() {
        return ebookCode;
    }

    public void setEbookCode(String ebookCode) {
        this.ebookCode = ebookCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<EbookAuthor> getAuthors() {
        return authors;
    }

    public void setAuthors(List<EbookAuthor> authors) {
        this.authors = authors;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(String imageLinks) {
        this.imageLinks = imageLinks;
    }

    public String getWebReaderLink() {
        return webReaderLink;
    }

    public void setWebReaderLink(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }


    @Override
    public String toString() {
        return "Ebook{" +
                "ebookId='" + ebookId + '\'' +
                ", ebookCode='" + ebookCode + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", publishedDate='" + publishedDate + '\'' +
                ", publisher='" + publisher + '\'' +
                ", description='" + description + '\'' +
                ", imageLinks='" + imageLinks + '\'' +
                ", webReaderLink='" + webReaderLink + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isDeleted=" + isDeleted +
                '}';
    }


}
