package com.project.app.hadiyankp.service.impl;

import com.project.app.hadiyankp.entity.library.Book;
import com.project.app.hadiyankp.repository.BookRepository;
import com.project.app.hadiyankp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    public BookServiceImpl() {
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
}
