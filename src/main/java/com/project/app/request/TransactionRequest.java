package com.project.app.request;

import com.project.app.entity.TransactionDetail;
import com.project.app.entity.User;

import java.util.List;

public class TransactionRequest {
    private User user;
    private List<TransactionDetail> transactionDetails;

    public TransactionRequest() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<TransactionDetail> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(List<TransactionDetail> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }
}
