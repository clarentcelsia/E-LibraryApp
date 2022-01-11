package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_transaction")
public class UserTransaction {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String userTransactionId;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @CreatedDate
    @Column(updatable = false)
    private Date transactionDate;

    @OneToMany(targetEntity = UserTransactionDetail.class, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<UserTransactionDetail> transactionDetails;

    private Boolean isDeleted;

    @PrePersist
    private void prePersist(){
        if(this.transactionDate == null) this.transactionDate = new Date();
        if(this.isDeleted == null) this.isDeleted = false;
    }

    public UserTransaction() {
    }

    public UserTransaction(String userTransactionId, Date transactionDate) {
        this.userTransactionId = userTransactionId;
        this.transactionDate = transactionDate;
    }

    public String getUserTransactionId() {
        return userTransactionId;
    }

    public void setUserTransactionId(String userTransactionId) {
        this.userTransactionId = userTransactionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public List<UserTransactionDetail> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(List<UserTransactionDetail> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
