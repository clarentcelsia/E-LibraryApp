package com.project.app.service.impl;

import com.project.app.entity.TransactionDetail;
import com.project.app.repository.TransactionDetailRepository;
import com.project.app.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionDetailServiceImpl implements TransactionDetailService<TransactionDetail> {

    @Autowired
    TransactionDetailRepository repository;

    @Override
    public TransactionDetail save(TransactionDetail transactionDetail) {
        return repository.save(transactionDetail);
    }
}
