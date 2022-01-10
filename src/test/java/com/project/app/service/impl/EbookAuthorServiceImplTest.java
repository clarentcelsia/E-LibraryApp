package com.project.app.service.impl;

import com.project.app.entity.EbookAuthor;
import com.project.app.repository.EbookAuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EbookAuthorServiceImplTest {

    @InjectMocks
    EbookAuthorServiceImpl service;

    @Mock
    EbookAuthorRepository repository;

    @Test
    public void whenSaveAuthor_thenReturnEbookAuthor(){
        EbookAuthor author = new EbookAuthor("EB01", "Johannes");
        EbookAuthor expected = repository.save(author);
        EbookAuthor actual = service.saveAuthor(author);
        assertEquals(expected, actual);
    }

}