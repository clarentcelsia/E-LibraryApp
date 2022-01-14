package com.project.app.service.impl;

import com.project.app.entity.ProductionBook;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.files.Files;
import com.project.app.repository.ProductionBookRepository;
import com.project.app.response.PageResponse;
import com.project.app.service.ProductionBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class ProductionBookServiceImpl implements ProductionBookService {

    @Autowired
    ProductionBookRepository productionBookRepository;

    @Autowired
    FileServiceImpl service;

    @Override
    public ProductionBook save(ProductionBook productionBook, MultipartFile... multipartFiles) {
        List<String> strings = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            Files files = service.saveMultipartFile(file);

            String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/files/" + files.getFileId())
                    .toUriString();
            strings.add(url);
        }

        if (strings.size() == 2) {
            productionBook.setImageUrl(strings.get(0));
            productionBook.setDownloadedLink(strings.get(1));
        } else {
            if (strings.size() == 3) {
                productionBook.setImageUrl(strings.get(0));
                productionBook.setPreviewLink(strings.get(1));
                productionBook.setDownloadedLink(strings.get(2));
            } else {
                System.out.println("Error: data is not detected");
            }
        }
        return productionBookRepository.save(productionBook);
    }

    @Override
    public Page<ProductionBook> getAll(Pageable pageable) {
        return productionBookRepository.findAll(pageable);
    }

    @Override
    public ProductionBook getById(String id) {
        return productionBookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("ProductionError: Book with id " + id + " not found"));
    }

    @Override
    public ProductionBook updateWithMultipart(ProductionBook productionBook, MultipartFile... multipartFiles) {
        ProductionBook book = getById(productionBook.getProductionBookId());
        //"http://localhost:8080/files/4028e4867e1ecabf017e1ecafab10000"

        String imageUrl = book.getImageUrl();
        String preview = book.getPreviewLink();
        String download = book.getDownloadedLink();

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

        productionBook.setUser(book.getUser());
        return save(productionBook, multipartFiles);
    }

    @Override
    public ProductionBook update(ProductionBook productionBook) {
        getById(productionBook.getProductionBookId());
        return productionBookRepository.save(productionBook);
    }


    @Override
    public void deleteById(String id) {
        ProductionBook book = getById(id);
        productionBookRepository.delete(book);
    }

}
