package com.project.app.service.impl;

import com.project.app.entity.ProductionBook;
import com.project.app.entity.UserTransaction;
import com.project.app.entity.UserTransactionDetail;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.UserTransactionRepository;
import com.project.app.service.ProductionBookService;
import com.project.app.service.TransactionDetailService;
import com.project.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserTransactionServiceImpl implements TransactionService<UserTransaction> {

    @Autowired
    UserTransactionRepository repository;

    @Autowired
    TransactionDetailService<UserTransactionDetail> userTransactionDetailService;

    @Autowired
    ProductionBookService bookService;

    @Override
    public UserTransaction createTransaction(UserTransaction request) {

        for (UserTransactionDetail detail : request.getTransactionDetails()) {
            ProductionBook book = bookService.getById(detail.getBook().getProductionBookId());
            if(!(book.getStock() - detail.getQty() < 0)) {
                book.setStock(book.getStock() - detail.getQty());
                bookService.update(book);

                detail.setPrice(book.getPrice());
                detail.setSubtotal(detail.getPrice() * detail.getQty());
                userTransactionDetailService.save(detail);
            }else {
                return null;
            }
        }
        return repository.save(request);
    }

    @Override
    public Page<UserTransaction> getTransactions(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public UserTransaction getTransactionById(String id) {
        return repository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Error: data by id " + id + " not found"));
    }
}
