package com.project.app.hadiyankp.service;

import com.project.app.hadiyankp.dto.BookDTO;
import com.project.app.hadiyankp.entity.library.Books;
import com.project.app.hadiyankp.request.BookRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    Books getBookById(String id);

    Books createBook(BookRequest request, MultipartFile cover, MultipartFile books);

    Books updateBook(Books book);

    Books updateBookWithMultipart(BookRequest books, MultipartFile image, MultipartFile file);

    Page<Books> getBooks(BookDTO bookDTO, Pageable pageable);

    void deleteBook(String id);
}
