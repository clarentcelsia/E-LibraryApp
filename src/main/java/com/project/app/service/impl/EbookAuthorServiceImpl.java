package com.project.app.service.impl;

import com.project.app.entity.EbookAuthor;
import com.project.app.repository.EbookAuthorRepository;
import com.project.app.service.EbookAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class EbookAuthorServiceImpl implements EbookAuthorService {

    @Autowired
    EbookAuthorRepository ebookAuthorRepository;

    @Override
    public EbookAuthor saveAuthor(EbookAuthor author) {
        return ebookAuthorRepository.save(author);
    }

    @Override
    public Page<EbookAuthor> filter(Specification<EbookAuthor> specification, Pageable pageable) {
        return ebookAuthorRepository.findAll(specification, pageable);
    }
}
