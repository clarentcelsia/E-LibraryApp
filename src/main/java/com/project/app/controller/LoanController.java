package com.project.app.controller;

import com.project.app.dto.LoanDTO;
import com.project.app.entity.Loan;
import com.project.app.entity.ReturnBook;
import com.project.app.response.PageResponse;
import com.project.app.response.WebResponse;
import com.project.app.service.LoanService;
import com.project.app.service.ReturnBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @Autowired
    private ReturnBookService returnBookService;

    @GetMapping
    public ResponseEntity<PageResponse<Loan>> getAllLoan(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "returnStatus") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "dateBorrow", required = false) String dateBorrow
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(direction),sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        String message = String.format("data halaman ke %d", page+1);
        LoanDTO dto = new LoanDTO(status,dateBorrow);
        Page<Loan> pagedLoan = loanService.getAll(dto, pageable);

        PageResponse<Loan> response = new PageResponse<>(
                pagedLoan.getContent(), message,
                pagedLoan.getTotalElements(), pagedLoan.getTotalPages(),
                page+1 , size
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<WebResponse<Loan>> getOneLoan(@PathVariable("loanId") String id){
        Loan loan = loanService.getById(id);
        WebResponse<Loan> response = new WebResponse<>("getting loan",loan);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{loanId}/status")
    public ResponseEntity<WebResponse<ReturnBook>> getReturnBookStatus(@PathVariable("loanId") String id){
        Loan loan = loanService.getById(id);
        ReturnBook returnBook = returnBookService.loadReturnBookByLoan(loan);
        WebResponse<ReturnBook> response = new WebResponse<>("getting loan status",returnBook);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<WebResponse<String>> deleteLoan(@PathVariable("loanId") String id){
        String deleteMessage = loanService.deleteById(id);
        WebResponse<String> response = new WebResponse<>(deleteMessage, null);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/transaction")
    public ResponseEntity<WebResponse<Loan>> createTransactionLoan(@RequestBody Loan loan){
        Loan transaction = loanService.createTransaction(loan);
        WebResponse<Loan> response = new WebResponse<>("creating transaction", transaction);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
