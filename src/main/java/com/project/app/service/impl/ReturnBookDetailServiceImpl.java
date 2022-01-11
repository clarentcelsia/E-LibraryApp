package com.project.app.service.impl;

import com.project.app.entity.ReturnBookDetail;
import com.project.app.repository.ReturnBookDetailRepository;
import com.project.app.service.ReturnBookDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReturnBookDetailServiceImpl implements ReturnBookDetailService {
    @Autowired
    private ReturnBookDetailRepository repository;

    @Override
    public ReturnBookDetail create(ReturnBookDetail returnBookDetail) {
        return repository.save(returnBookDetail);
    }
}
