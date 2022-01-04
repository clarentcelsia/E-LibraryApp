package com.project.app.service;

import com.project.app.entity.Loan;
import com.project.app.entity.ReturnBook;

import java.util.List;

public interface ReturnBookService {
    public ReturnBook createTransaction(ReturnBook returnBook);
    public List<ReturnBook> getReturnBooks();

    public ReturnBook loadReturnBookByLoan(Loan loan);
}
