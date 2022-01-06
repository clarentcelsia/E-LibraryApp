package com.project.app.service;

import com.project.app.entity.Transactions;
import com.project.app.request.TransactionRequest;

import java.util.List;

public interface TransactionService {

    Transactions createTransaction(TransactionRequest request);

    List<Transactions> getTransactions();

    Transactions getTransactionById(String id);

}
