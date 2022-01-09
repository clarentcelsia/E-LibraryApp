package com.project.app.service.impl;

import com.project.app.entity.Research;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.files.Files;
import com.project.app.repository.ResearchRepository;
import com.project.app.response.PageResponse;
import com.project.app.service.FileService;
import com.project.app.service.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ResearchServiceImpl implements ResearchService {

    @Autowired
    ResearchRepository repository;

    @Autowired
    FileService fileService;

    @Override
    public Research saveResearch(Research research) {
        return repository.save(research);
    }

    @Override
    public Page<Research> getResearch(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Research getById(String id) {
        return repository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Error: data by id " + id + " not found"));
    }

    @Override
    public void deleteResearch(String id) {
        Research research = getById(id);
        research.setDeleted(false);
        saveResearch(research);
    }
}
