package com.project.app.service.impl;

import com.project.app.entity.Plan;
import com.project.app.entity.Transaction;
import com.project.app.entity.TransactionDetail;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.TransactionRepository;
import com.project.app.service.PlanService;
import com.project.app.service.TransactionDetailService;
import com.project.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class TransactionServiceImpl implements TransactionService<Transaction> {

    @Autowired
    TransactionRepository repository;

    @Autowired
    PlanService planService;

    @Autowired
    TransactionDetailService<TransactionDetail> transactionDetailService;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        Transaction save = repository.save(transaction);

        int total = 0;
        for(TransactionDetail detail : save.getDetails()){
            Plan planById = planService.getPlanById(detail.getPlan().getPlanId());

            detail.setPrice(planById.getPrice());
            detail.setPlan(planById);
            detail.setTransaction(save);
            TransactionDetail transactionDetail = transactionDetailService.save(detail);

            total += transactionDetail.getPrice();
        }

        save.setGrandtotal(total);
        return repository.save(save);
    }

    @Override
    public Page<Transaction> getTransactions(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Transaction getTransactionById(String id) {
        return repository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Error: transaction id " + id + " not found"));
    }
}
