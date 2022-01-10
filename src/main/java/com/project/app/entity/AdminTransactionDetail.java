package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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


    public AdminTransactionDetail() {
    }

    public AdminTransactionDetail(String adminTransactionDetailId, Integer price, Integer qty) {
        this.adminTransactionDetailId = adminTransactionDetailId;
        this.price = price;
        this.qty = qty;
    }

    public String getAdminTransactionDetailId() {
        return adminTransactionDetailId;
    }

    public void setAdminTransactionDetailId(String adminTransactionDetailId) {
        this.adminTransactionDetailId = adminTransactionDetailId;
    }

    public AdminTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(AdminTransaction transaction) {
        this.transaction = transaction;
    }

    public BookSale getBook() {
        return book;
    }

    public void setBook(BookSale book) {
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
