package com.project.app.service.impl;

import com.project.app.entity.BookSale;
import com.project.app.entity.ProductionBook;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.files.Files;
import com.project.app.repository.BookSaleRepository;
import com.project.app.response.PageResponse;
import com.project.app.service.BookSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookSaleServiceImpl implements BookSaleService {

    @Autowired
    FileServiceImpl service;

    @Autowired
    BookSaleRepository repository;

    @Override
    public BookSale saveBookSale(BookSale item, MultipartFile...multipartFiles) {
        List<String> strings = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            Files files = service.saveMultipartFile(file);

            String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/files/" + files.getFileId())
                    .toUriString();
            strings.add(url);
        }

        if (strings.size() == 2) {
            item.setImageUrl(strings.get(0));
            item.setDownloadLink(strings.get(1));
        }else {
            if(strings.size() == 3){
                item.setImageUrl(strings.get(0));
                item.setPreviewLink(strings.get(1));
                item.setDownloadLink(strings.get(2));
            }else{
                System.out.println("Error: data is not detected");
            }
        }
        return repository.save(item);
    }

    @Override
    public PageResponse<BookSale> getBookSales(Pageable pageable) {
        Page<BookSale> page = repository.findAll(pageable);
        PageResponse<BookSale> response = new PageResponse<>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages()
        );
        return response;
    }

    @Override
    public BookSale getBookSaleById(String id) {
        return repository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Error: data with id " + id + " not found"));
    }

    @Override
    public BookSale updateWithMultipart(BookSale bookSale, MultipartFile... multipartFiles) {
        BookSale book = getBookSaleById(bookSale.getBookSaleId());
        //"http://localhost:8080/files/4028e4867e1ecabf017e1ecafab10000"

        String imageUrl = book.getImageUrl();
        String preview = book.getPreviewLink();
        String download = book.getDownloadLink();

        List<String> url = new ArrayList<>();
        url.add(imageUrl);
        url.add(preview);
        url.add(download);

        for (String str : url) {
            String localhost = str.substring(7);
            String[] urls = localhost.trim().split("/");
            String id = urls[urls.length - 1];
            service.deleteFileById(id);
        }

        return saveBookSale(bookSale, multipartFiles);
    }

    @Override
    public BookSale updateBookSale(BookSale bookSale) {
        BookSale container = getBookSaleById(bookSale.getBookSaleId());
        container.setTitle(bookSale.getTitle());
        container.setDescription(bookSale.getDescription());
        container.setStock(bookSale.getStock());
        container.setPrice(bookSale.getPrice());
        container.setAvailableForBookPhysic(bookSale.getAvailableForBookPhysic());
        return repository.save(container);
    }

    @Override
    public void deleteBookSale(String id) {
        BookSale container = getBookSaleById(id);
        repository.delete(container);
    }
}
