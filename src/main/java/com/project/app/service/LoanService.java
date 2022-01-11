package com.project.app.service;

import com.project.app.dto.LoanDTO;
import com.project.app.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LoanService {
    public String deleteById(String id);
    public Loan getById(String id);
    public Page<Loan> getAll(LoanDTO dto, Pageable pageable);

    public Loan createTransaction(Loan loan);

    public Loan update(Loan loan);

}
