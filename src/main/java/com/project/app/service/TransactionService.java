package com.project.app.service;

import com.project.app.entity.Transaction;
import com.project.app.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction);

    Page<Transaction> getTransactions(Pageable pageable);

    Transaction getTransactionById(String id);
}
