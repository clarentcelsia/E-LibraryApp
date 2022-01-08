package com.project.app.service;


import com.project.app.entity.BookSale;
import com.project.app.response.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BookSaleService {

    BookSale saveBookSale(BookSale item, MultipartFile...multipartFiles);

    PageResponse<BookSale> getBookSales(Pageable pageable);

    BookSale getBookSaleById(String id);

    BookSale updateWithMultipart(BookSale bookSale, MultipartFile...multipartFiles);

    BookSale updateBookSale(BookSale bookSale);

    void deleteBookSale(String id);
}
