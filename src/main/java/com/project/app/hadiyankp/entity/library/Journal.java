package com.project.app.hadiyankp.entity.library;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "journal")
public class Journal {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String doi;
    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date publishDate;
    private String files;
    @CreatedDate
    @Column(updatable = false)
    private Date createdAt;

    @LastModifiedDate
    private Date updateAt;

    private Boolean isDeleted;

    @PrePersist
    private void insertBefore() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }

        if (this.updateAt == null) {
            this.updateAt = new Date();
        }

        if (isDeleted == null)
            isDeleted = false;
    }

    @PreUpdate
    private void updateBefore() {
        this.updateAt = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Journal() {
    }

    public Journal(String id, String doi, String title, String description, Date publishDate, String files, Date createdAt, Date updateAt, Boolean isDeleted) {
        this.id = id;
        this.doi = doi;
        this.title = title;
        this.description = description;
        this.publishDate = publishDate;
        this.files = files;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.isDeleted = isDeleted;
    }
}

