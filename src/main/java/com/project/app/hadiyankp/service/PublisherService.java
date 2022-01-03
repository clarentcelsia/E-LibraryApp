package com.project.app.hadiyankp.service;

import com.project.app.hadiyankp.dto.PublisherDTO;
import com.project.app.hadiyankp.entity.library.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublisherService {
    Publisher createPublisher(Publisher publisher);
    Publisher getById(String id);
    Page<Publisher>listWithPage(Pageable pageable, PublisherDTO publisherDTO);
    String deletePublisher(String id);
    Publisher updatePublisher(Publisher publisher);

}
