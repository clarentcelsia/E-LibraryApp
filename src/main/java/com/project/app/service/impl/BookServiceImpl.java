package com.project.app.service.impl;

import com.project.app.dto.BookDTO;
import com.project.app.entity.Book;
import com.project.app.entity.library.Author;
import com.project.app.entity.library.Files;
import com.project.app.exception.NotFoundException;
import com.project.app.repository.BookRepository;
import com.project.app.request.BookRequest;
import com.project.app.service.AuthorService;
import com.project.app.service.BookService;
import com.project.app.specification.BookSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.project.app.utils.Utility.RESPONSE_NOT_FOUND;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    FileService fileService;

    @Autowired
    AuthorService authorService;

    @Override
    public Book getBookById(String id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(RESPONSE_NOT_FOUND, id)));
    }

    @Override
    public Book createBook(BookRequest request, MultipartFile cover, MultipartFile books) {

        Files coverFile = fileService.saveMultipartFile(cover);
        Files bookFile = fileService.saveMultipartFile(books);

        String urlCover = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/" + coverFile.getFileId())
                .toUriString();
        request.setCover(urlCover);
        String urlBooks = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/" + bookFile.getFileId())
                .toUriString();
        request.setBookUrl(urlBooks);

        return mapBookToDB(request);
    }

    @Override
    public Book updateBook(Book book) {
        getBookById(book.getId());
        return bookRepository.save(book);
    }

    @Override
    public Book updateBookWithMultipart(BookRequest books, MultipartFile image, MultipartFile book) {
        Book getBook = getBookById(books.getId());

        //handle file
        String cover = getBook.getCover();
        String bookFile = getBook.getBookUrl();

        List<String> urls = new ArrayList<>();
        urls.add(cover);
        urls.add(bookFile);

        for (String url: urls) {
            String removeHttp = url.substring(7);
            String[] strs = removeHttp.trim().split("/");
            String fileId = strs[strs.length - 1];
            fileService.deleteFile(fileId);
        }

        return createBook(books, image, book);
    }

    @Override
    public Page<Book> getBooks(BookDTO bookDTO, Pageable pageable) {
        Specification<Book> booksSpecification = BookSpecification.getSpecification(bookDTO);
        return bookRepository.findAll(booksSpecification, pageable);
    }

    @Override
    public void deleteBook(String id) {
        getBookById(id);
        bookRepository.deleteById(id);
    }

    private Book mapBookToDB(BookRequest request) {
        Book book;
        if (request.getId() == null) {
            book = new Book();
        } else {
            book = getBookById(request.getId());
        }

        book.setCover(request.getCover());
        book.setTitle(request.getTitle());
        book.setDescription(request.getDescription());
        book.setPublisher(request.getPublisher());
        book.setPublishedDate(request.getPublishedDate());
        book.setBookUrl(request.getBookUrl());
        book.setStock(request.getStock());
        book.setCategory(request.getCategory());
        book.setType(request.getType());
        book.setTotalRating(request.getTotalRating());
        book.setTotalReview(request.getTotalReview());

        Book save = bookRepository.save(book);

        if(request.getId() == null) {
            for (String author : request.getAuthors()) {
                Author newAuthor = new Author();
                newAuthor.setName(author);
                Author createdAuthor = authorService.create(newAuthor);

                save.getAuthors().add(createdAuthor);
                createdAuthor.getBooks().add(save);
                authorService.create(createdAuthor);
            }
            return bookRepository.save(save);
        }
        return save;
    }

}
