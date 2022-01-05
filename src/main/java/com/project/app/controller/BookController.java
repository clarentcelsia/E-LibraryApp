package com.project.app.controller;

import com.project.app.entity.Book;
import com.project.app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//SAMPLE
@RestController
@RequestMapping("/api/v2/books")
public class BookController {

    @Autowired
    BookService service;

    @PostMapping
    public Book createBook(@RequestBody Book book){
        return service.createBook(book);
    }
}
