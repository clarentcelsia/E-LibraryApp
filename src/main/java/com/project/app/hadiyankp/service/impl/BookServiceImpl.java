package com.project.app.hadiyankp.service.impl;

import com.project.app.hadiyankp.entity.library.Book;
import com.project.app.hadiyankp.entity.library.Files;
import com.project.app.hadiyankp.repository.BookRepository;
import com.project.app.hadiyankp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    FileService fileService;

    public BookServiceImpl() {
    }


    @Override
    public Book createBook(Book book, MultipartFile cover, MultipartFile books) {
        String coverName = StringUtils.cleanPath(cover.getOriginalFilename());
        String booksName = StringUtils.cleanPath(books.getOriginalFilename());
        try {
            Files filesCover = new Files(coverName,cover.getBytes(),cover.getContentType());
            Files filesBooks = new Files(booksName,books.getBytes(),books.getContentType());
            fileService.saveFile(filesCover);
            fileService.saveFile(filesBooks);
            String urlCover= ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/"+ filesCover.getFileId())
                    .toUriString();
            book.setCover(urlCover);
            String urlBooks= ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/"+ filesBooks.getFileId())
                    .toUriString();
            book.setBook(urlBooks);
            bookRepository.save(book);

        }catch (Exception e){
            e.printStackTrace();
        }
        return bookRepository.save(book);
    }
}
