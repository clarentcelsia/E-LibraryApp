package com.project.app.controller;

import com.project.app.entity.AdminTransaction;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.app.util.Utility.RESPONSE_CREATE_SUCCESS;
import static com.project.app.util.Utility.RESPONSE_GET_SUCCESS;

@RestController
@RequestMapping("/api/v11/transactions")
public class AdminTransactionController {

    @Autowired
    TransactionService<AdminTransaction> transactionService;

    @PostMapping("/admin")
    public ResponseEntity<Response<AdminTransaction>> createTransactions(
            @RequestBody AdminTransaction request
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(
                        RESPONSE_CREATE_SUCCESS,
                        transactionService.createTransaction(request)));
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<Response<AdminTransaction>> getTransactionById(
            @PathVariable(name = "id") String id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        RESPONSE_GET_SUCCESS,
                        transactionService.getTransactionById(id)));
    }

    @GetMapping("/admin")
    public ResponseEntity<Response<PageResponse<AdminTransaction>>> getTransactions(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        RESPONSE_GET_SUCCESS,
                        transactionService.getTransactions(pageable)));
    }
}
