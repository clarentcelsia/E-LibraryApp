package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ebook_author")
public class EbookAuthor {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String ebookAuthorId;

    private String name;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Ebook> ebooks;

    public EbookAuthor() {
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

//    public Set<Ebook> getEbooks() {
//        return ebooks;
//    }
//
//    public void setEbooks(Set<Ebook> ebooks) {
//        this.ebooks = ebooks;
//    }
}
