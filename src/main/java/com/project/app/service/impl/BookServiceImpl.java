package com.project.app.service.impl;

import com.project.app.entity.Book;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.BookRepository;
import com.project.app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

//Sample
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository repository;

    @Override
    public Book createBook(Book book) {
        return repository.save(book);
    }

    @Override
    public Book getBookById(String id) {
        return repository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Error: book by id " + id + " not found"));
    }
}
