package com.project.app.service.impl;

import com.project.app.entity.BookSale;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.files.Files;
import com.project.app.repository.BookSaleRepository;
import com.project.app.service.BookSaleService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public BookSale saveIntoContainer(BookSale item, MultipartFile...multipartFiles) {
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
    public List<BookSale> getContainers() {
        return repository.findAll();
    }

    @Override
    public BookSale getContainerById(String id) {
        return repository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Error: data with id " + id + " not found"));
    }

    @Override
    public BookSale updateContainer(BookSale bookSale) {
        BookSale container = getContainerById(bookSale.getBookSaleId());
        container.setTitle(bookSale.getTitle());
        container.setDescription(bookSale.getDescription());
        container.setStock(bookSale.getStock());
        container.setPrice(bookSale.getPrice());
        container.setAvailableForBookPhysic(bookSale.getAvailableForBookPhysic());
        return repository.save(container);
    }

    @Override
    public void deleteContainer(String id) {
        BookSale container = getContainerById(id);
        repository.delete(container);
    }
}
