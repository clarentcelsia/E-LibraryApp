package com.project.app.repository;

import com.project.app.entity.Loan;
import com.project.app.entity.ReturnBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReturnBookRepository extends JpaRepository<ReturnBook, String> {
    public Optional<ReturnBook> findByLoan(Loan loan);
    Page<ReturnBook> findAll(Specification<ReturnBook> specification, Pageable pageable);
}