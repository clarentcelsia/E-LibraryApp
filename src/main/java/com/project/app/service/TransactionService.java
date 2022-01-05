package com.project.app.service;

import com.project.app.entity.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction);

    List<Transaction> getTransactions();

    Transaction getTransactionById(String id);
}
