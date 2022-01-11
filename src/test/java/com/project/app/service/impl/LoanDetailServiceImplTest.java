package com.project.app.service.impl;

import com.project.app.entity.Book;
import com.project.app.entity.Loan;
import com.project.app.entity.LoanDetail;
import com.project.app.entity.User;
import com.project.app.repository.LoanDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoanDetailServiceImplTest {
    @InjectMocks
    LoanDetailServiceImpl service;

    @Mock
    LoanDetailRepository repository;


    private LoanDetail inputLoanDetail;
    private LoanDetail outputLoanDetail;
    private User jannes;
    private Book book;
    private Loan loan;
    @BeforeEach
    public void setup(){
        jannes = new User("user-1", "jannes");
        book = new Book("book-1", "buku mtk", 100);
        loan = new Loan();
        inputLoanDetail = new LoanDetail(null,loan , book ,1);
        outputLoanDetail = new LoanDetail("loan-detail-1",loan , book ,1);

    }

    @Test
    public void create_Should_AddTopictoCollection(){
        Mockito.when(repository.save(inputLoanDetail)).thenReturn(outputLoanDetail);

        LoanDetail savedLoan = service.create(inputLoanDetail);
        List<LoanDetail> loanDetails = new ArrayList<>();
        loanDetails.add(savedLoan);

        // actual
        Mockito.when(repository.findAll()).thenReturn(loanDetails);
        assertEquals(repository.findAll().size(), 1);
    }
}