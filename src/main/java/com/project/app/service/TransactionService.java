package com.project.app.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService<T> {

    T createTransaction(T request);

    Page<T> getTransactions(Pageable pageable);

    T getTransactionById(String id);

}
