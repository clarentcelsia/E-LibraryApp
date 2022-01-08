package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ebook_author")
public class EbookAuthor {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String ebookAuthorId;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authors", cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JsonBackReference
    private List<Ebook> ebooks = new ArrayList<>();

    public EbookAuthor() {
    }

    public EbookAuthor(String ebookAuthorId, String name) {
        this.ebookAuthorId = ebookAuthorId;
        this.name = name;
    }

    public EbookAuthor(String name) {
        this.name = name;
    }

    public String getEbookAuthorId() {
        return ebookAuthorId;
    }

    public void setEbookAuthorId(String ebookAuthorId) {
        this.ebookAuthorId = ebookAuthorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ebook> getEbooks() {
        return ebooks;
    }

    public void setEbooks(List<Ebook> ebooks) {
        this.ebooks = ebooks;
    }

}
