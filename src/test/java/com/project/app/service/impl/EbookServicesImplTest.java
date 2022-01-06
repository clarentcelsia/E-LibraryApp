package com.project.app.service.impl;

import com.project.app.entity.Ebook;
import com.project.app.repository.EbookRepository;
import com.project.app.request.EbookAPI;
import com.project.app.service.EbookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EbookServicesImplTest {

    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    EbookService service;

    @Mock
    EbookRepository repository;

    @Test
    public void whenEbookSavedSuccessfully_thenReturnEbook(){
        List<String> strings = new ArrayList<>();
        strings.add("hello");

        EbookAPI api = new EbookAPI(
                "API01", "How to use adobe photoshop?",
                strings, "1993", "erlangga",
                "description", "imageLink",
                "webReaderLink");

        when(service.saveEbookToDB(api)).thenReturn(any(Ebook.class));

    }
}