package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
