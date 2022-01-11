package com.project.app.service.impl;

import com.project.app.entity.*;
import com.project.app.repository.ReturnBookDetailRepository;
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
class ReturnBookDetailServiceImplTest {
    @InjectMocks
    ReturnBookDetailServiceImpl service;

    @Mock
    ReturnBookDetailRepository repository;


    private ReturnBookDetail inputReturnDetail;
    private ReturnBookDetail outputReturnDetail;
    private User jannes;
    private Book book;
    private ReturnBook returnBook;
    @BeforeEach
    public void setup(){
        jannes = new User("user-1", "jannes");
        book = new Book("book-1", "buku mtk", 100);
        returnBook = new ReturnBook();
        inputReturnDetail = new ReturnBookDetail(null, returnBook, book ,1, "all oke", null);
        outputReturnDetail = new ReturnBookDetail("return-detail-1", returnBook, book ,1, "all oke", 0);
    }

    @Test
    public void create_Should_AddReturnBooktoCollection(){
        Mockito.when(repository.save(inputReturnDetail)).thenReturn(outputReturnDetail);

        ReturnBookDetail savedBookDetail = service.create(inputReturnDetail);
        List<ReturnBookDetail> loanDetails = new ArrayList<>();
        loanDetails.add(savedBookDetail);

        // actual
        Mockito.when(repository.findAll()).thenReturn(loanDetails);
        assertEquals(repository.findAll().size(), 1);
    }

    // ========== test transaction ===================
}