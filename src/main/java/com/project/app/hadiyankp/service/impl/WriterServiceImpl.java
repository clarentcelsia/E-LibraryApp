package com.project.app.hadiyankp.service.impl;

import com.project.app.hadiyankp.entity.library.Writer;
import com.project.app.hadiyankp.repository.WriterRepository;
import com.project.app.hadiyankp.service.WriterService;
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
