package com.project.app.repository;

import com.project.app.entity.Loan;
import com.project.app.entity.ReturnBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReturnBookRepository extends JpaRepository<ReturnBook, String> {
    public Optional<ReturnBook> findByLoan(Loan loan);
}