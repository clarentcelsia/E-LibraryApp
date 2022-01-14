package com.project.app.service.impl;

import com.project.app.entity.Plan;
import com.project.app.entity.Transaction;
//import com.project.app.entity.TransactionDetail;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.TransactionRepository;
import com.project.app.service.PlanService;
import com.project.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.project.app.utils.Utility.SLOT;
import static com.project.app.utils.Utility.UNLIMITED_SLOT_SIZE;


@Service
@Transactional
public class TransactionServiceImpl implements TransactionService<Transaction> {

    @Autowired
    TransactionRepository repository;

    @Autowired
    PlanService planService;


    @Override
    public Transaction createTransaction(Transaction transaction) {
        Transaction save = repository.save(transaction);

        String planId = save.getPlan().getPlanId();
        Plan planById1 = planService.getPlanById(planId);

        save.setGrandtotal(planById1.getPrice());
        save.setPlan(planById1);
        return repository.save(save);
    }

    @Override
    public Page<Transaction> getTransactions(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Transaction getTransactionById(String id) {
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Error: transaction id " + id + " not found"));
    }

    public Transaction update(Transaction transaction){
        Transaction transactionById = getTransactionById(transaction.getTransactionId());
        transactionById.setDeleted(true);
        repository.save(transactionById);

//        Plan plan = planService.getPlanById(transaction.getPlan().getPlanId());
//        if(!plan.getPlan().equalsIgnoreCase("basic")){
//            slotService.deleteSlotByClientId(UNLIMITED_SLOT_SIZE, transaction.getClient().getClientId());
//        }

        Transaction transaction1 = transaction;
        transaction1.setTransactionId(null);
        return createTransaction(transaction1);
    }
}
