package com.project.app.controller;

import com.project.app.entity.AdminTransaction;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.TransactionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import static com.project.app.utils.Utility.*;


@RestController
@RequestMapping("/admin-transactions")
public class AdminTransactionController {

    @Autowired
    TransactionService<AdminTransaction> transactionService;

    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    paramType = "header",
                    required = true,
                    dataType = "string"
            ))
    @PostMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<AdminTransaction>> createTransactions(
            @RequestBody AdminTransaction request
    ){
        AdminTransaction transaction = transactionService.createTransaction(request);

        Response<AdminTransaction> response;
        if(transaction == null) {
            response = new Response<>(String.format(INVALID, OUT_OF_STOCK), null);
        }else {
            response = new Response<>(RESPONSE_CREATE_SUCCESS, transaction);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
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
        Page<AdminTransaction> transactions = transactionService.getTransactions(pageable);
        PageResponse<AdminTransaction> response = new PageResponse<>(
                transactions.getContent(),
                transactions.getTotalElements(),
                transactions.getTotalPages(),
                page,
                size
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        RESPONSE_GET_SUCCESS, response));
    }
}
