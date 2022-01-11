package com.project.app.service.impl;

import com.project.app.entity.library.Writer;
import com.project.app.repository.WriterRepository;
import com.project.app.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriterServiceImpl implements WriterService {

    @Autowired
    WriterRepository repository;

    @Override
    public Writer saveWriter(Writer writer) {
        return repository.save(writer);
    }
}
