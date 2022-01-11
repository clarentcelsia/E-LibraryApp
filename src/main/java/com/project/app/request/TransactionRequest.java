package com.project.app.request;

import com.project.app.entity.User;

import java.util.List;

public class TransactionRequest<T> {
    private User user;
    private List<T> transactionDetails;

    public TransactionRequest() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<T> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(List<T> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }
}
