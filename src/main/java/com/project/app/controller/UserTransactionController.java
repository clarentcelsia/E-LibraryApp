package com.project.app.controller;

import com.project.app.entity.AdminTransaction;
import com.project.app.entity.User;
import com.project.app.entity.UserTransaction;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.TransactionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import static com.project.app.utils.Utility.*;
import static com.project.app.utils.Utility.OUT_OF_STOCK;


@RestController
@RequestMapping("/user-transactions")
public class UserTransactionController {

    @Autowired
    TransactionService<UserTransaction> transactionService;

    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    paramType = "header",
                    required = true,
                    dataType = "string"
            ))
    @PostMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Response<UserTransaction>> createTransactions(
            @RequestBody UserTransaction request
    ){
        UserTransaction transaction = transactionService.createTransaction(request);

        Response<UserTransaction> response;
        if(transaction == null) {
            response = new Response<>(String.format(INVALID, OUT_OF_STOCK), null);
        }else {
            response = new Response<>(RESPONSE_CREATE_SUCCESS, transaction);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Response<UserTransaction>> getTransactionById(
            @PathVariable(name = "id") String id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        RESPONSE_GET_SUCCESS,
                        transactionService.getTransactionById(id)));
    }

    @GetMapping("/user")
    public ResponseEntity<Response<PageResponse<UserTransaction>>> getTransactions(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<UserTransaction> transactions = transactionService.getTransactions(pageable);
        PageResponse<UserTransaction> response = new PageResponse<>(
                transactions.getContent(),
                transactions.getTotalElements(),
                transactions.getTotalPages(),
                page,
                size
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, response));
    }
}
