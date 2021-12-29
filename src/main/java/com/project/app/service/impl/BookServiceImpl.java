package com.project.app.service.impl;

import com.project.app.entity.library.Book;
import com.project.app.repository.BookRepository;
import com.project.app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book createBook(Book book) {
        return null;
    }
}
