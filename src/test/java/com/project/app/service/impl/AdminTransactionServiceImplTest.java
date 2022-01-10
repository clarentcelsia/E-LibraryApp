package com.project.app.service.impl;

import com.project.app.entity.AdminTransaction;
import com.project.app.entity.AdminTransactionDetail;
import com.project.app.entity.BookSale;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.AdminTransactionRepository;
import com.project.app.service.BookSaleService;
import com.project.app.service.TransactionDetailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminTransactionServiceImplTest {

    @InjectMocks
    AdminTransactionServiceImpl service;

    @Mock
    AdminTransactionRepository repository;

    @Mock
    BookSaleService bookSaleService;

    @Mock
    AdminTransactionDetailServiceImpl adminTransactionDetailService;

    @Test
    public void whenCreateTransaction_thenReturnSuccess() {

        AdminTransaction transaction = mock(AdminTransaction.class);

        when(repository.save(any(AdminTransaction.class))).thenReturn(transaction);

        AdminTransaction adminTransaction = service.createTransaction(transaction);

        assertThat(adminTransaction).isNotNull();

        assertEquals(transaction, adminTransaction);
    }

    @Test
    public void whenCreateTransaction_thenReturnAdminTransaction(){
        BookSale bookSale = new BookSale();
        bookSale.setBookSaleId("B01");
        bookSale.setStock(10);
        bookSale.setPrice(10000);
        when(bookSaleService.getBookSaleById(any(String.class))).thenReturn(bookSale);
        when(bookSaleService.updateBookSale(any(BookSale.class))).thenReturn(bookSale);

        AdminTransactionDetail transactionDetail = new AdminTransactionDetail();
        transactionDetail.setAdminTransactionDetailId("AD01");
        transactionDetail.setBook(bookSale);
        transactionDetail.setQty(2);
        when(adminTransactionDetailService.save(any(AdminTransactionDetail.class))).thenReturn(transactionDetail);

        List<AdminTransactionDetail> detailList = new ArrayList<>();
        detailList.add(transactionDetail);

        AdminTransaction adminTransaction = new AdminTransaction();
        adminTransaction.setTransactionId("A01");
        adminTransaction.setTransactionDetails(detailList);
        when(repository.save(any(AdminTransaction.class))).thenReturn(adminTransaction);

        AdminTransaction transaction = service.createTransaction(adminTransaction);

        assertThat(transaction).isNotNull();
    }

    @Test
    public void whenGetTransaction_thenReturnPageOfAdminTransaction() {

        Pageable pageable = PageRequest.of(0, 5);

        AdminTransaction transaction = new AdminTransaction("A01");

        List<AdminTransaction> adminTransactions = new ArrayList<>();
        adminTransactions.add(transaction);

        Page<AdminTransaction> page = new PageImpl<>(adminTransactions, pageable, 1L);

        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        Page<AdminTransaction> transactions = service.getTransactions(pageable);

        assertEquals(page, transactions);
    }

    @Test
    public void whenGetTransactionById_thenReturnAdminTransaction() {

        AdminTransaction transaction = new AdminTransaction("A01");

        given(repository.findById(any(String.class))).willReturn(Optional.of(transaction));

        AdminTransaction adminTransaction = service.getTransactionById(transaction.getTransactionId());

        assertThat(adminTransaction).isNotNull();

    }

    @Test
    public void whenGetTransactionByIdFailed_thenThrowError() {
        assertThrows(ResourceNotFoundException.class, () -> {
            service.getTransactionById("A02");
        });

    }
}