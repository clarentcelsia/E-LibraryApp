package com.project.app.service;


import com.project.app.entity.BookSale;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookSaleService {

    BookSale saveIntoContainer(BookSale item, MultipartFile...multipartFiles);

    List<BookSale> getContainers();

    BookSale getContainerById(String id);

    BookSale updateContainer(BookSale purchaseContainer);

    void deleteContainer(String id);
}
