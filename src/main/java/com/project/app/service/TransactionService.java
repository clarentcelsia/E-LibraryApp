package com.project.app.service;

import com.project.app.entity.Transactions;
import com.project.app.request.TransactionRequest;

public interface TransactionService {

    Transactions createTransaction(TransactionRequest request);

}
