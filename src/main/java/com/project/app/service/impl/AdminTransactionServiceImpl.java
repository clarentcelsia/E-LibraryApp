package com.project.app.service.impl;

import com.project.app.entity.BookSale;
import com.project.app.entity.AdminTransaction;
import com.project.app.entity.AdminTransactionDetail;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.AdminTransactionRepository;
import com.project.app.request.TransactionRequest;
import com.project.app.response.PageResponse;
import com.project.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.project.app.util.Utility.RESPONSE_ID_NOT_FOUND;

@Service
@Transactional
public class AdminTransactionServiceImpl implements TransactionService<AdminTransaction> {

    @Autowired
    AdminTransactionRepository transactionRepository;

    @Autowired
    BookSaleService bookSaleService;

    @Autowired
    TransactionDetailService<AdminTransactionDetail> transactionDetailService;

    @Override
    public AdminTransaction createTransaction(AdminTransaction request) {
        AdminTransaction transactions = transactionRepository.save(request);

        //response
        for (AdminTransactionDetail detail : transactions.getTransactionDetails()) {
            //update book stock
            BookSale book = bookSaleService.getBookSaleById(detail.getBook().getBookSaleId());
            book.setStock(book.getStock() - detail.getQty());
            BookSale sale = bookSaleService.updateBookSale(book);

            //save (admin) transaction detail
            detail.setBook(sale);
            detail.setPrice(sale.getPrice());
            detail.setSubtotal(detail.getPrice() * detail.getQty());
            transactionDetailService.save(detail);
        }

        //save (admin) transaction
//        AdminTransaction save = transactionRepository.save(transactions);
//        save.setTransactionDetails(adminTransactionDetails);
        return transactions;
    }

    @Override
    public PageResponse<AdminTransaction> getTransactions(Pageable pageable) {
        Page<AdminTransaction> page = transactionRepository.findAll(pageable);
        PageResponse<AdminTransaction> response = new PageResponse<>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages()
        );
        return response;
    }

    @Override
    public AdminTransaction getTransactionById(String id) {
        return transactionRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(RESPONSE_ID_NOT_FOUND, id)));
    }
}
