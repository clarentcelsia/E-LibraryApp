package com.project.app.controller;

import com.project.app.entity.Transactions;
import com.project.app.request.TransactionRequest;
import com.project.app.response.Response;
import com.project.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v11/booksale")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<Response<Transactions>> createTransactions(
            @RequestBody TransactionRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(
                        "Success: data create successfully",
                        transactionService.createTransaction(request)));
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Response<Transactions>> getTransactionById(
            @PathVariable(name = "id") String id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        "Success: data create successfully",
                        transactionService.getTransactionById(id)));
    }

    @GetMapping("/transactions")
    public ResponseEntity<Response<List<Transactions>>> getTransactions(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        "Success: data create successfully",
                        transactionService.getTransactions()));
    }
}
