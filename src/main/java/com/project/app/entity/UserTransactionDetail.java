package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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

    public UserTransactionDetail() {
    }

    public String getUserTransactionDetailId() {
        return userTransactionDetailId;
    }

    public void setUserTransactionDetailId(String userTransactionDetailId) {
        this.userTransactionDetailId = userTransactionDetailId;
    }

    public UserTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(UserTransaction transaction) {
        this.transaction = transaction;
    }

    public ProductionBook getBook() {
        return book;
    }

    public void setBook(ProductionBook book) {
        this.book = book;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
