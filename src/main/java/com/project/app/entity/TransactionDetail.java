package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "transaction_details")
public class TransactionDetail {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String transactionDetailId;

    @ManyToOne(targetEntity = Transactions.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    @JsonBackReference
    private Transactions transaction;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToOne(targetEntity = BookSale.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_sale_id")
    private BookSale book;

    private Integer price;
    private Integer subtotal;
    private Integer qty;

    public TransactionDetail() {
    }

    public String getTransactionDetailId() {
        return transactionDetailId;
    }

    public void setTransactionDetailId(String transactionDetailId) {
        this.transactionDetailId = transactionDetailId;
    }

    public Transactions getTransaction() {
        return transaction;
    }

    public void setTransaction(Transactions transaction) {
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

    @Override
    public String toString() {
        return "TransactionDetail{" +
                "transactionDetailId='" + transactionDetailId + '\'' +
                ", transaction=" + transaction +
                ", book=" + book +
                ", price=" + price +
                ", subtotal=" + subtotal +
                ", qty=" + qty +
                '}';
    }
}
