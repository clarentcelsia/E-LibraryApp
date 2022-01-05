package com.project.app.controller;

import com.project.app.entity.Clients;
import com.project.app.entity.Transaction;
import com.project.app.response.Response;
import com.project.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v10/client/transactions")
public class TransactionController {

    @Autowired
    TransactionService service;

    @PostMapping
    public ResponseEntity<Response<Transaction>> createTransaction(
            @RequestBody Transaction transaction
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>("Succeed: transaction saved successfully!", service.createTransaction(transaction)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Transaction>> getTransactionById(
            @PathVariable(name = "id") String id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: transaction saved successfully!", service.getTransactionById(id)));

    }

    @GetMapping
    public ResponseEntity<Response<List<Transaction>>> getTransactions() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: get transaction successfully!", service.getTransactions()));
    }
}
