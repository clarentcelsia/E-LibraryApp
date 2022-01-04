package com.project.app.hadiyankp.controller;

import com.project.app.hadiyankp.entity.library.Book;
import com.project.app.hadiyankp.service.BookService;
import com.project.app.hadiyankp.util.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/books"})
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<WebResponse<Book>> createBook(@RequestBody Book book){
        Book createBook = bookService.createBook(book);
        WebResponse<Book> response = new WebResponse<>("Data Book Has Been Created", createBook);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }



}
