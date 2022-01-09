package com.project.app.hadiyankp.controller;

import com.project.app.hadiyankp.entity.library.Book;
import com.project.app.hadiyankp.entity.library.Journal;
import com.project.app.hadiyankp.service.BookService;
import com.project.app.hadiyankp.util.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/books"})
public class BookController {
    @Autowired
    private BookService bookService;


    @PostMapping(
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_OCTET_STREAM_VALUE
            },
            produces = "application/json"
    )
    public ResponseEntity<WebResponse<Book>> create(
            @RequestPart(name = "book") Book book,
            @RequestPart(name = "cover") MultipartFile cover,
            @RequestPart(name = "books") MultipartFile books
    ) {
        Book createBook = bookService.createBook(book,cover,books);
        WebResponse<Book> response = new WebResponse<>("Data Books Has Been Created", createBook);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
//        return journalServiceImpl.createJournal(journal,photo);
    }


}
