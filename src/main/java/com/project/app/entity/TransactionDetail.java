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
    @ManyToOne(targetEntity = ProductionBook.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "production_book_id")
    private ProductionBook productionBook;

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

    public ProductionBook getProductionBook() {
        return productionBook;
    }

    public void setProductionBook(ProductionBook productionBook) {
        this.productionBook = productionBook;
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
                ", productionBook=" + productionBook +
                ", price=" + price +
                ", subtotal=" + subtotal +
                ", qty=" + qty +
                '}';
    }
}
