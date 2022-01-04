package com.project.app.hadiyankp.entity.library;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import org.hibernate.annotations.GenericGenerator;
//import org.springframework.data.annotation.LastModifiedDate;
//
//import javax.persistence.*;
//import java.util.Date;
//@Entity
//@Table(name = "journal_author")
//public class JournalAuthor {
////    @Id
////    @GeneratedValue(generator = "system-uuid")
////    @GenericGenerator(name = "system-uuid", strategy = "uuid")
////    private String id;
////
////    @ManyToOne(targetEntity = Journal.class,fetch = FetchType.EAGER)
////    @JoinColumn(name = "journal_id")
////    @JsonBackReference
////    private Journal journal;
////
////    @ManyToOne(targetEntity = Author.class,fetch = FetchType.EAGER)
////    @JoinColumn(name = "author_id")
////    @JsonBackReference
////    private Author author;
////
////    @Column(updatable = false)
////    private Date createdAt;
////
////    @LastModifiedDate
////    private Date updateAt;
////
////    @PrePersist
////    private void insertBefore() {
////        if (this.createdAt == null) {
////            this.createdAt = new Date();
////        }
////
////        if (this.updateAt == null) {
////            this.updateAt = new Date();
////        }
////    }
////
////    @PreUpdate
////    private void updateBefore() {
////        this.updateAt = new Date();
////    }
////
////    public JournalAuthor() {
////    }
////
////    public String getId() {
////        return id;
////    }
////
////    public void setId(String id) {
////        this.id = id;
////    }
////
////    public Journal getJournal() {
////        return journal;
////    }
////
////    public void setJournal(Journal journal) {
////        this.journal = journal;
////    }
////
////    public Author getAuthor() {
////        return author;
////    }
////
////    public void setAuthor(Author author) {
////        this.author = author;
////    }
////
////    public Date getCreatedAt() {
////        return createdAt;
////    }
////
////    public void setCreatedAt(Date createdAt) {
////        this.createdAt = createdAt;
////    }
////
////    public Date getUpdateAt() {
////        return updateAt;
////    }
////
////    public void setUpdateAt(Date updateAt) {
////        this.updateAt = updateAt;
////    }
////
////    public JournalAuthor(String id, Journal journal, Author author, Date createdAt, Date updateAt) {
////        this.id = id;
////        this.journal = journal;
////        this.author = author;
////        this.createdAt = createdAt;
////        this.updateAt = updateAt;
////    }
//}
