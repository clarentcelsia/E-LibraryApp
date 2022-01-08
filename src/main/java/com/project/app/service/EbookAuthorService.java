package com.project.app.service;

import com.project.app.entity.EbookAuthor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface EbookAuthorService {

    EbookAuthor saveAuthor(EbookAuthor author);

}
