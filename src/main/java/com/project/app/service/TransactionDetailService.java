package com.project.app.service;

import com.project.app.entity.AdminTransactionDetail;

public interface TransactionDetailService<T> {

    T save(T transactionDetail);
}
