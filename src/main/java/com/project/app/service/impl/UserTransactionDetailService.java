package com.project.app.service.impl;

import com.project.app.entity.UserTransactionDetail;
import com.project.app.repository.UserTransactionDetailRepository;
import com.project.app.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTransactionDetailService implements TransactionDetailService<UserTransactionDetail> {

    @Autowired
    UserTransactionDetailRepository repository;

    @Override
    public UserTransactionDetail save(UserTransactionDetail transactionDetail) {
        return repository.save(transactionDetail);
    }
}
