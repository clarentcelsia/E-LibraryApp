package com.project.app.controller;

import com.project.app.dto.LoanDTO;
import com.project.app.dto.ReturnDTO;
import com.project.app.entity.Loan;
import com.project.app.entity.ReturnBook;
import com.project.app.response.PageResponse;
import com.project.app.response.WebResponse;
import com.project.app.service.ReturnBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/returns")
public class ReturnTransactionController {
    @Autowired
    private ReturnBookService returnBookService;

    @PostMapping
    public ResponseEntity<WebResponse<ReturnBook>> createTransaction(@RequestBody ReturnBook returnBook){
        ReturnBook transaction = returnBookService.createTransaction(returnBook);
        WebResponse<ReturnBook> response = new WebResponse<>("creating transaction", returnBook);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageResponse<ReturnBook>> getAllReturnBooks(
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
                returnBooks.getContent(), message,
                returnBooks.getTotalElements(), returnBooks.getTotalPages(),
                page+1 , size
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
