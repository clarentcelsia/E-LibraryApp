package com.project.app.hadiyankp.service.impl;

import com.project.app.hadiyankp.dto.PublisherDTO;
import com.project.app.hadiyankp.entity.library.Category;
import com.project.app.hadiyankp.entity.library.Publisher;
import com.project.app.hadiyankp.entity.library.Subject;
import com.project.app.hadiyankp.exception.NotFoundException;
import com.project.app.hadiyankp.repository.PublisherReporsitory;
import com.project.app.hadiyankp.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherServiceImpl implements PublisherService {
    @Autowired
    private PublisherReporsitory publisherReporsitory;

    @Override
    public Publisher createPublisher(Publisher publisher) {
        return publisherReporsitory.save(publisher);
    }

    @Override
    public Publisher getById(String id) {
        Optional<Publisher> publisher = publisherReporsitory.findById(id);
        if (publisher.isPresent()) {
            return publisher.get();
        }else {
            throw new NotFoundException(String.format("Publisher with id %s not found", id));
        }
    }

    @Override
    public Page<Publisher> listWithPage(Pageable pageable, PublisherDTO publisherDTO) {
        return null;
    }

    @Override
    public String deletePublisher(String id) {
        Publisher publisher = getById(id);
        publisherReporsitory.delete(publisher);
        return String.format("Publisher with Id %s has been deleted",publisher.getId());
    }

    @Override
    public Publisher updatePublisher(Publisher publisher) {
        getById(publisher.getId());
        return publisherReporsitory.save(publisher);
    }
}
