package com.project.app.service;

import com.project.app.dto.BookDTO;
import com.project.app.entity.Book;
import com.project.app.request.BookRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {

    Book getBookById(String id);

    Book createBook(BookRequest request, MultipartFile cover, MultipartFile books);

    Book updateBook(Book book);

    Book updateBookWithMultipart(BookRequest books, MultipartFile image, MultipartFile file);

    Page<Book> getBooks(BookDTO bookDTO, Pageable pageable);

    void deleteBook(String id);
}
