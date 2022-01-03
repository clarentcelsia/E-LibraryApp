package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loan_detail")
public class LoanDetail {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @ManyToOne(targetEntity = Loan.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", updatable = false)
    @JsonBackReference
    private Loan loan;

    @ManyToOne(targetEntity = Book.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", updatable = false)
    private Book book;

    @Column(nullable = false)
    private Integer qty;

    @Override
    public String toString() {
        return "LoanDetail{" +
                "id='" + id + '\'' +
                ", book=" + book +
                ", qty=" + qty +
                '}';
    }
}
