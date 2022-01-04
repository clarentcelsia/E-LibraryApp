package com.project.app.service.impl;

import com.project.app.entity.ProductionBook;
import com.project.app.entity.Transactions;
import com.project.app.entity.TransactionDetail;
import com.project.app.repository.TransactionRepository;
import com.project.app.request.TransactionRequest;
import com.project.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ProductionBookService productionBookService;

    @Autowired
    TransactionDetailService transactionDetailService;

    @Autowired
    UserService userService;

    @Override
    public Transactions createTransaction(TransactionRequest request) {
        Transactions transactions = new Transactions();

        transactions.setUser(userService.getUserById(request.getUser().getUserId()));

        Transactions save = transactionRepository.save(transactions);

        List<TransactionDetail> details = new ArrayList<>();
        for(TransactionDetail detail: request.getTransactionDetails()){
            ProductionBook productionBook = productionBookService.getById(detail.getProductionBook().getProductionBookId());
            productionBook.setStock(productionBook.getStock() - detail.getQty());
            ProductionBook update = productionBookService.update(productionBook);

            detail.setTransaction(save);
            detail.setProductionBook(productionBook);
            detail.setPrice(productionBook.getPrice());
            detail.setSubtotal(detail.getPrice() * detail.getQty());
            detail.setTransaction(save);

            TransactionDetail save1 = transactionDetailService.save(detail);
            details.add(save1);
        }

        save.setTransactionDetails(details);
        return save;
    }
}
