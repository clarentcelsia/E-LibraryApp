package com.project.app.controller;

import com.project.app.entity.Transactions;
import com.project.app.request.TransactionRequest;
import com.project.app.response.Response;
import com.project.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v3/productions")
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
}
