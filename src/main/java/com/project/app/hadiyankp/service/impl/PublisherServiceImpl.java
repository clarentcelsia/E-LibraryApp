package com.project.app.hadiyankp.service.impl;

import com.project.app.hadiyankp.dto.PublisherDTO;
import com.project.app.hadiyankp.entity.library.Publisher;
import com.project.app.hadiyankp.exception.NotFoundException;
import com.project.app.hadiyankp.repository.PublisherRepository;
import com.project.app.hadiyankp.service.PublisherService;
import com.project.app.hadiyankp.specification.PublisherSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherServiceImpl implements PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public Publisher createPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    public Publisher getById(String id) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        if (publisher.isPresent()) {
            return publisher.get();
        }else {
            throw new NotFoundException(String.format("Publisher with id %s not found", id));
        }
    }

    @Override
    public Page<Publisher> listWithPage(Pageable pageable, PublisherDTO publisherDTO) {
        Specification<Publisher> specification = PublisherSpecification.getSpecification(publisherDTO);
        return publisherRepository.findAll(specification,pageable);
    }

    @Override
    public String deletePublisher(String id) {
        Publisher publisher = getById(id);
        publisherRepository.delete(publisher);
        return String.format("Publisher with Id %s has been deleted",publisher.getId());
    }

    @Override
    public Publisher updatePublisher(Publisher publisher) {
        getById(publisher.getId());
        return publisherRepository.save(publisher);
    }
}
