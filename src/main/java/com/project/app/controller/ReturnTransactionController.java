package com.project.app.controller;

import com.project.app.dto.ReturnDTO;
import com.project.app.entity.ReturnBook;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.ReturnBookService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/returns")
public class ReturnTransactionController {
    @Autowired
    private ReturnBookService returnBookService;

    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    paramType = "header",
                    required = true,
                    dataType = "string"
            ))
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<ReturnBook>> createTransaction(@RequestBody ReturnBook returnBook){
        ReturnBook transaction = returnBookService.createTransaction(returnBook);
        Response<ReturnBook> response = new Response<>("creating transaction", transaction);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<PageResponse<ReturnBook>>> getAllReturnBooks(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "dateReturn") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "totalQty", required = false) Integer totalQty,
            @RequestParam(name = "totalPenalty", required = false) Integer totalPenalty,
            @RequestParam(name = "dateReturn", required = false) String dateReturn
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(direction),sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        String message = String.format("data halaman ke %d", page+1);
        ReturnDTO dto = new ReturnDTO(totalQty,totalPenalty, dateReturn);
        Page<ReturnBook> returnBooks = returnBookService.getReturnBooks(dto, pageable);

        PageResponse<ReturnBook> response = new PageResponse<>(
                returnBooks.getContent(),
                returnBooks.getTotalElements(), returnBooks.getTotalPages(),
                page+1 , size
        );
        return ResponseEntity.ok(new Response<>(message, response));
    }
}
