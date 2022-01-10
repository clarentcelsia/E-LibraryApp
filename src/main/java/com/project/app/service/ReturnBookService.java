package com.project.app.service;

import com.project.app.dto.ReturnDTO;
import com.project.app.entity.Loan;
import com.project.app.entity.ReturnBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ReturnBookService {
    public ReturnBook createTransaction(ReturnBook returnBook);
    public Page<ReturnBook> getReturnBooks(ReturnDTO dto, Pageable pageable);

    public ReturnBook loadReturnBookByLoan(Loan loan);
}
