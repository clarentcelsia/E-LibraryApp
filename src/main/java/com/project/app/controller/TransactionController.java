package com.project.app.controller;

import com.project.app.entity.Transaction;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.project.app.utils.Utility.RESPONSE_CREATE_SUCCESS;
import static com.project.app.utils.Utility.RESPONSE_GET_SUCCESS;

@RestController
@RequestMapping("/api/v1/client/transactions")
public class TransactionController {

    @Autowired
    TransactionServiceImpl service;

    @PostMapping
    public ResponseEntity<Response<Transaction>> createTransaction(
            @RequestBody Transaction transaction
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(RESPONSE_CREATE_SUCCESS, service.createTransaction(transaction)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Transaction>> getTransactionById(
            @PathVariable(name = "id") String id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, service.getTransactionById(id)));

    }

    @GetMapping
    public ResponseEntity<Response<PageResponse<Transaction>>> getTransactions(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size
    ) {
        Page<Transaction> transactions = service.getTransactions(PageRequest.of(page, size));
        PageResponse<Transaction> response = new PageResponse<>(
                transactions.getContent(),
                transactions.getTotalElements(),
                transactions.getTotalPages(),
                page,
                size
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, response));
    }

    @PostMapping("/slot")
    public ResponseEntity<Response<Transaction>> upsertTransaction(
            @RequestBody Transaction transaction
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(RESPONSE_CREATE_SUCCESS, service.update(transaction)));
    }
}
