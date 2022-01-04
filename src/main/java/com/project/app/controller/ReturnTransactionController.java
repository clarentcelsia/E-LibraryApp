package com.project.app.controller;

import com.project.app.entity.ReturnBook;
import com.project.app.service.ReturnBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/returns")
public class ReturnTransactionController {
    @Autowired
    private ReturnBookService returnBookService;

    @PostMapping
    public ReturnBook createTransaction(@RequestBody ReturnBook returnBook){
        return returnBookService.createTransaction(returnBook);
    }

    @GetMapping
    public List<ReturnBook> getAllReturnBooks(){
        return returnBookService.getReturnBooks();
    }
}
