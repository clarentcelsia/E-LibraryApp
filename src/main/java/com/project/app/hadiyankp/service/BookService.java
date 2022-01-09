package com.project.app.hadiyankp.service;

import com.project.app.hadiyankp.entity.library.Book;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    Book createBook(Book book, MultipartFile cover,MultipartFile books);
}
