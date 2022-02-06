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
@Table(name = "admin_transaction_detail")
public class AdminTransactionDetail {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String adminTransactionDetailId;

    @ManyToOne(targetEntity = AdminTransaction.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    @JsonBackReference
    private AdminTransaction transaction;

    @ManyToOne(targetEntity = BookSale.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_sale_id")
    private BookSale book;

    private Integer price;
    private Integer qty;
    private Integer subtotal;
}
