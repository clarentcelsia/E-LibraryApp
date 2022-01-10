package com.project.app.service.impl;

import com.project.app.dto.ReturnDTO;
import com.project.app.entity.*;
import com.project.app.repository.ReturnBookRepository;
import com.project.app.service.ReturnBookDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReturnBookServiceImplTest {

    @InjectMocks
    ReturnBookServiceImpl service;

    @Mock
    ReturnBookRepository repository;

    @Mock
    ReturnBookDetailService returnBookDetailService;

    private ReturnBook inputReturnBook;
    private ReturnBook outpuReturnBook;
    private ReturnBookDetail returnBookDetailOne;
    private Loan loanOne;
    private User jannes;
    private Book bookOne;
    @BeforeEach
    public void setup(){
        jannes = new User("user-1", "jannes");
        loanOne = new Loan("loan-1", 1, false, jannes, null, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.of(LocalDate.of(2022,1,13), LocalTime.now()));
        bookOne = new Book("book-1", "buku mtk", 100);
        returnBookDetailOne = new ReturnBookDetail("return-detail-1", null , bookOne ,1, "all oke", 0);

        inputReturnBook = new ReturnBook(null, loanOne, null, null, null, null , 1000 );
        outpuReturnBook = new ReturnBook("return-book-1", loanOne, new ArrayList<>(), 1, LocalDateTime.now(), 0, 1000);
        outpuReturnBook.getReturnBookDetails().add(returnBookDetailOne);
    }

    @Test
    public void getAll_ShouldReturn_AllPagedReturnBooks(){
        List<ReturnBook> returnBooks = new ArrayList<>();
        returnBooks.add(outpuReturnBook);

        ReturnDTO dto = new ReturnDTO(1, null, null);
        Sort sort = Sort.by(Sort.Direction.ASC, "totalQty");
        Pageable pageable = PageRequest.of(0,1, sort);

        Page<ReturnBook> returnBookPage = new PageImpl<>(returnBooks, pageable, returnBooks.size());

        // actual
        Mockito.when(repository.findAll(Mockito.any(Specification.class),Mockito.any(Pageable.class))).thenReturn(returnBookPage);

        // expected
        Page<ReturnBook> pageReturnBook = service.getReturnBooks(dto, pageable);
        assertEquals(pageReturnBook.getTotalElements(), 1);
    }

}