package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "return_book_detail")
public class ReturnBookDetail {
    @Id
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @GeneratedValue(generator = "uuid-generator")
    private String id;

    @ManyToOne(targetEntity = ReturnBook.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "return_id", updatable = false)
    @JsonBackReference
    private ReturnBook returnBook;

    @ManyToOne(targetEntity = Book.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", updatable = false)
    private Book book;

    @Column (nullable = false)
    private Integer qty;

    @Column (nullable = false)
    private String returnInfo;

    @Column (nullable = false)
    private Integer penaltyFee;
}
