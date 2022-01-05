package com.project.app.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {

    //basic premium
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String transactionId;

    @OneToOne(targetEntity = Clients.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Clients clients;

    @ManyToOne(targetEntity = Plan.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    private Integer price;

    @CreatedDate
    @Column(updatable = false)
    private Date transactionDate;

    private Boolean isDeleted;

    @PrePersist
    private void prePersist(){
        if (this.isDeleted == null) this.isDeleted = false;
        if (this.transactionDate == null) this.transactionDate = new Date();
    }

    public Transaction() {
    }

    public Transaction(String transactionId, Clients clients, Plan plan, Date transactionDate, Boolean isDeleted) {
        this.transactionId = transactionId;
        this.clients = clients;
        this.plan = plan;
        this.transactionDate = transactionDate;
        this.isDeleted = isDeleted;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Clients getClients() {
        return clients;
    }

    public void setClients(Clients clients) {
        this.clients = clients;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
