package com.project.app.service.impl;

import com.project.app.entity.EbookAuthor;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.EbookAuthorRepository;
import com.project.app.service.EbookAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookAuthorServiceImpl implements EbookAuthorService {

    @Autowired
    EbookAuthorRepository ebookAuthorRepository;

    @Override
    public EbookAuthor saveAuthor(EbookAuthor author) {
        return ebookAuthorRepository.save(author);
    }

    @Override
    public EbookAuthor getAuthorById(String id) {
        return ebookAuthorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Error: author with id " + id + " not found"));
    }

}
