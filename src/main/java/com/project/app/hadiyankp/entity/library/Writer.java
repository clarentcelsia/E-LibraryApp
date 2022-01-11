package com.project.app.hadiyankp.entity.library;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "writers")
public class Writer {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(updatable = false)
    private String writer;

    @Column(updatable = false)
    private Date createdAt;

    @LastModifiedDate
    private Date updateAt;


    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "writers",cascade = {
            CascadeType.MERGE,CascadeType.PERSIST
    })
    @JsonBackReference
    private List<Journal>journals = new ArrayList<>();

    @PrePersist
    private void insertBefore() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }

        if (this.updateAt == null) {
            this.updateAt = new Date();
        }
    }

    @PreUpdate
    private void updateBefore() {
        this.updateAt = new Date();
    }


    public Writer(String id, String writer, Date createdAt, Date updateAt, List<Journal> journals) {
        this.id = id;
        this.writer = writer;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.journals = journals;
    }
    public String getId() {
        return id;
    }

    public Writer() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
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

    public Writer(Writer strWriter) {
    }

    public List<Journal> getJournals() {
        return journals;
    }

    public void setJournals(List<Journal> journals) {
        this.journals = journals;
    }
}
