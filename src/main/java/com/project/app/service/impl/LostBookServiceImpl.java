package com.project.app.service.impl;

import com.project.app.dto.LostBookDTO;
import com.project.app.entity.LostBookReport;
import com.project.app.repository.LostBookReportRepository;
import com.project.app.service.LostBookService;
import com.project.app.specification.LostBookSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class LostBookServiceImpl implements LostBookService {
    @Autowired
    LostBookReportRepository repository;

    @Override
    public LostBookReport create(LostBookReport lostBookReport) {
        return repository.save(lostBookReport);
    }

    @Override
    public Page<LostBookReport> getAll(LostBookDTO dto, Pageable pageable) {
        Specification<LostBookReport> specification = LostBookSpec.getSpecification(dto);
        return repository.findAll(specification, pageable);
    }
}
