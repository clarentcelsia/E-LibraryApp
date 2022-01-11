package com.project.app.service.impl;

import com.project.app.dto.LoanDTO;
import com.project.app.entity.Book;
import com.project.app.entity.Loan;
import com.project.app.entity.LoanDetail;
import com.project.app.entity.User;
import com.project.app.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {
    @InjectMocks
    LoanServiceImpl service;

    @Mock
    LoanRepository repository;

    @Mock
    LoanDetailServiceImpl loanDetailService;

    private Loan inputLoan;
    private Loan outputLoan;
    private User jannes;
    @BeforeEach
    public void setup(){
        jannes = new User("user-1", "jannes");
        inputLoan = new Loan(null, null, null, jannes, null, null, null, LocalDateTime.now());
        outputLoan = new Loan("loan-1", null, false, jannes, null, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    public void getById_ShouldReturn_RequestedLoan_When_IdExist(){
        Mockito.when(repository.findById("loan-1")).thenReturn(Optional.of(outputLoan));

        Loan getLoan = service.getById("loan-1");
        assertEquals(getLoan.getId(), "loan-1");
    }

    @Test
    public void getById_ShouldThrows_ExceptionWithMessageAndNotFoundCode_when_IdNotExist(){
        Mockito.when(repository.findById("asal")).thenReturn(Optional.empty());

        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> service.getById("asal"));
        assertEquals(e.getReason(), "loan with id asal not found");
        assertEquals(e.getRawStatusCode(), 404);
    }

    @Test
    public void deleteById_ShouldReturn_message(){
        Mockito.when(repository.findById("loan-1")).thenReturn(Optional.of(outputLoan));

        String message = service.deleteById("loan-1");
        assertEquals(message,"loan with id loan-1 is deleted");
    }

    @Test
    public void updateLoan(){
        Loan requestLoan =new Loan("loan-1", null, true, jannes, null, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        Mockito.when(repository.save(requestLoan)).thenReturn(requestLoan);

        Loan updatedLoan = service.update(requestLoan);
        assertEquals(updatedLoan.getReturnStatus(), true);
    }

    @Test
    public void getAll_ShouldReturn_AllPagedLoans(){
        List<Loan> loans = new ArrayList<>();
        loans.add(outputLoan);

        LoanDTO dto = new LoanDTO("false", null);
        Sort sort = Sort.by(Sort.Direction.ASC, "returnStatus");
        Pageable pageable = PageRequest.of(0,1, sort);

        Page<Loan> loanPage = new PageImpl<>(loans, pageable, loans.size());

        // actual
        Mockito.when(repository.findAll(Mockito.any(Specification.class),Mockito.any(Pageable.class))).thenReturn(loanPage);

        // expected
        Page<Loan> pagedLoans = service.getAll(dto, pageable);
        assertEquals(pagedLoans.getTotalElements(), 1);
    }

    // ============== transaction test =====================
    @Test
    public void createTransaction_ShouldReturn_LoanTransaction(){
        // preparasi data
        LocalDateTime dateDue = LocalDateTime.of(LocalDate.of(2022, 1, 30), LocalTime.now());
        Book book = new Book("book-1", "mtk", 100);
        LoanDetail loanDetailOne = new LoanDetail("loan-detail-1", null, book, 1);

        Loan requestLoan =new Loan(null, null, null, jannes, null, new ArrayList<>(), null, dateDue);
        requestLoan.getLoanDetail().add(loanDetailOne);

        Loan outputLoan =new Loan("loan-1", 1, false, jannes, null, new ArrayList<>(), null, dateDue);
        outputLoan.getLoanDetail().add(loanDetailOne);


        // mock repo , actual
        Mockito.when(repository.save(Mockito.any(Loan.class))).thenReturn(outputLoan);
        Mockito.when(loanDetailService.create(Mockito.any(LoanDetail.class))).thenReturn(loanDetailOne);

        //expected
        Loan transaction = service.createTransaction(requestLoan);

        assertEquals(transaction.getId(), outputLoan.getId());
        assertEquals(transaction.getLoanDetail().get(0).getId(), loanDetailOne.getId());
    }

    @Test
    public void createTransaction_ShouldThrow_Exception_When_dateDueLessThandateBorrow(){
        // preparasi data
        LocalDateTime dateDue = LocalDateTime.of(LocalDate.of(2021, 1, 30), LocalTime.now());
        Book book = new Book("book-1", "mtk", 100);
        LoanDetail loanDetailOne = new LoanDetail("loan-detail-1", null, book, 1);

        Loan requestLoan =new Loan(null, null, null, jannes, null, new ArrayList<>(), null, dateDue);
        requestLoan.getLoanDetail().add(loanDetailOne);

        //expected
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.createTransaction(requestLoan));

        assertEquals(exception.getRawStatusCode(), 400);
        assertEquals(exception.getReason(), "dateDue should not earlier than dateBorrow");
    }

//    @Test == testing book saat merging
//    public void createTransaction_ShouldThrow_Exception_When_BookStockNotEnough(){
//        // preparasi data
//        LocalDateTime dateDue = LocalDateTime.of(LocalDate.of(2023, 1, 30), LocalTime.now());
//        Book book = new Book("book-1", "mtk", 0);
//        LoanDetail loanDetailOne = new LoanDetail("loan-detail-1", null, book, 1);
//
//        Loan requestLoan =new Loan(null, null, null, jannes, null, new ArrayList<>(), null, dateDue);
//        requestLoan.getLoanDetail().add(loanDetailOne);
//
//        //expected
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.createTransaction(requestLoan));
//
//        String errorMsg = String.format("book stock : %d is less than requested loan", book.getStock());
//        assertEquals(exception.getRawStatusCode(), 400);
//        assertEquals(exception.getReason(), errorMsg);
//    }
}