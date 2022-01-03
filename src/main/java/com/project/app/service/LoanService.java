package com.project.app.service;

import com.project.app.entity.Loan;

import java.util.List;

public interface LoanService {
    public Loan create(Loan loan);
    public Loan update(Loan loan);
    public String deleteById(String id);
    public Loan getById(String id);
    public List<Loan> getAll();
}
