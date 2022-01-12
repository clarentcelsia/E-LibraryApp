package com.project.app.service.impl;

import com.project.app.entity.LoanDetail;
import com.project.app.repository.LoanDetailRepository;
import com.project.app.service.LoanDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanDetailServiceImpl implements LoanDetailService {
    @Autowired
    private LoanDetailRepository loanDetailRepository;

    @Override
    public LoanDetail create(LoanDetail loanDetail) {
        return loanDetailRepository.save(loanDetail);
    }
}
