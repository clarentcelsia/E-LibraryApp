package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "client_transactions")
public class Transaction {

    //basic premium
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String transactionId;

    @ManyToOne(targetEntity = Clients.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Clients client;

    @OneToMany(targetEntity = TransactionDetail.class, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<TransactionDetail> details;

    @JsonFormat(
            pattern = "yyyy-MM-dd hh:mm:ss"
    )
    @CreatedDate
    @Column(updatable = false)
    private Date transactionDate;

    private Integer grandtotal;

    private Boolean isDeleted;

    @PrePersist
    private void prePersist(){
        if (this.isDeleted == null) this.isDeleted = false;
        if (this.transactionDate == null) this.transactionDate = new Date();
    }

    public Transaction() {
    }

    public Transaction(String transactionId, Clients client, List<TransactionDetail> details, Date transactionDate, Integer grandtotal, Boolean isDeleted) {
        this.transactionId = transactionId;
        this.client = client;
        this.details = details;
        this.transactionDate = transactionDate;
        this.grandtotal = grandtotal;
        this.isDeleted = isDeleted;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    public List<TransactionDetail> getDetails() {
        return details;
    }

    public void setDetails(List<TransactionDetail> details) {
        this.details = details;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getGrandtotal() {
        return grandtotal;
    }

    public void setGrandtotal(Integer grandtotal) {
        this.grandtotal = grandtotal;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}