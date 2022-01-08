package com.project.app.service;

import com.project.app.entity.AdminTransaction;
import com.project.app.request.TransactionRequest;
import com.project.app.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService<T> {

    T createTransaction(T request);

    PageResponse<T> getTransactions(Pageable pageable);

    T getTransactionById(String id);

}
