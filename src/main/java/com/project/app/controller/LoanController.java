package com.project.app.controller;

import com.project.app.dto.LoanDTO;
import com.project.app.entity.Loan;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @GetMapping
    public ResponseEntity<Response<PageResponse<Loan>>> getAllLoan(
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
                pagedLoan.getContent(), pagedLoan.getTotalElements(),
                pagedLoan.getTotalPages(), page, size
        );

        return ResponseEntity.ok(
                new Response<>(message, response));
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<Response<Loan>> getOneLoan(@PathVariable("loanId") String id){
        Loan loan = loanService.getById(id);
        Response<Loan> response = new Response<>("getting loan",loan);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<Response<String>> deleteLoan(@PathVariable("loanId") String id){
        String deleteMessage = loanService.deleteById(id);
        Response<String> response = new Response<>(deleteMessage, null);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/transaction")
    public ResponseEntity<Response<Loan>> createTransactionLoan(@RequestBody Loan loan){
        Loan transaction = loanService.createTransaction(loan);
        Response<Loan> response = new Response<>("creating transaction", transaction);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
