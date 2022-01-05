package com.project.app.service.impl;

import com.project.app.entity.Plan;
import com.project.app.entity.Transaction;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.TransactionRepository;
import com.project.app.service.PlanService;
import com.project.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository repository;

    @Autowired
    PlanService planService;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        //production slot
        String planId = transaction.getPlan().getPlanId();
        Plan planById = planService.getPlanById(planId);
        transaction.setPrice(planById.getPrice());
        return repository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactions() {
        return repository.findAll();
    }

    @Override
    public Transaction getTransactionById(String id) {
        return repository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Error: transaction id " + id + " not found"));
    }
}
