package com.project.app.service;

import com.project.app.entity.Book;

public interface BookService {

    Book createBook(Book book);

    Book getBookById(String id);

}
