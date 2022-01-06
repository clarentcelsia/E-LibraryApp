package com.project.app.service.impl;

import com.project.app.entity.BookSale;
import com.project.app.entity.Transactions;
import com.project.app.entity.TransactionDetail;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.TransactionRepository;
import com.project.app.request.TransactionRequest;
import com.project.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BookSaleService bookSaleService;

    @Autowired
    TransactionDetailService transactionDetailService;

    @Autowired
    UserService userService;

    @Override
    public Transactions createTransaction(TransactionRequest request) {
        Transactions transactions = new Transactions();

        transactions.setUser(userService.getUserById(request.getUser().getUserId()));

        //response
        List<TransactionDetail> userTrxDetails = new ArrayList<>();
        for (TransactionDetail detail : request.getTransactionDetails()) {
            BookSale book = bookSaleService.getContainerById(detail.getBook().getBookSaleId());
            book.setStock(book.getStock() - detail.getQty());
            BookSale sale = bookSaleService.updateContainer(book);

            detail.setBook(sale);
            detail.setPrice(sale.getPrice());
            detail.setSubtotal(detail.getPrice() * detail.getQty());
            TransactionDetail save1 = transactionDetailService.save(detail);
            userTrxDetails.add(save1);
        }

        Transactions save = transactionRepository.save(transactions);
        save.setTransactionDetails(userTrxDetails);
        return save;
    }

    @Override
    public List<Transactions> getTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transactions getTransactionById(String id) {
        return transactionRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Error: data with id " + id + " not found"));
    }
}
