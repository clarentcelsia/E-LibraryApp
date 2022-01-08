package com.project.app.service.impl;

import com.project.app.entity.AdminTransaction;
import com.project.app.entity.AdminTransactionDetail;
import com.project.app.entity.BookSale;
import com.project.app.entity.User;
import com.project.app.repository.AdminTransactionRepository;
import com.project.app.response.PageResponse;
import com.project.app.service.BookSaleService;
import com.project.app.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.awt.print.Book;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
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
    public void whenCreateTransaction_thenReturnAdminTransaction(){

        AdminTransaction transaction = mock(AdminTransaction.class);

        when(repository.save(any(AdminTransaction.class))).thenReturn(transaction);

        AdminTransaction adminTransaction = service.createTransaction(transaction);

        assertThat(adminTransaction).isNotNull();

        assertEquals(transaction, adminTransaction);
    }
}