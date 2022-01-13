package com.project.app.service.impl;

import com.project.app.entity.AdminTransactionDetail;
import com.project.app.repository.AdminTransactionDetailRepository;
//import com.project.app.service.TransactionDetailService;
import com.project.app.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminTransactionDetailServiceImpl implements TransactionDetailService<AdminTransactionDetail> {

    @Autowired
    AdminTransactionDetailRepository repository;

    @Override
    public AdminTransactionDetail save(AdminTransactionDetail transactionDetail) {
        return repository.save(transactionDetail);
    }
}
