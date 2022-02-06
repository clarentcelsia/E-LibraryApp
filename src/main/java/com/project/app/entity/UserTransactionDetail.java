package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_transaction_detail")
public class UserTransactionDetail {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String userTransactionDetailId;

    @ManyToOne(targetEntity = UserTransaction.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_transaction_id")
    @JsonBackReference
    private UserTransaction transaction;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToOne(targetEntity = ProductionBook.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "production_book_id")
    private ProductionBook book;

    private Integer price;
    private Integer qty;
    private Integer subtotal;
}
