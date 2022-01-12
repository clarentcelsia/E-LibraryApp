package com.project.app.service.impl;

import com.project.app.dto.LoanDTO;
import com.project.app.entity.Book;
import com.project.app.entity.Loan;
import com.project.app.entity.LoanDetail;
import com.project.app.repository.LoanRepository;
import com.project.app.service.BookService;
import com.project.app.service.LoanDetailService;
import com.project.app.service.LoanService;
import com.project.app.specification.LoanSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanDetailService loanDetailService;

    @Autowired
    private BookService bookService;

    @Override
    public String deleteById(String id) {
        Loan loan = getById(id);
        loanRepository.delete(loan);
        return String.format("loan with id %s is deleted", id);
    }

    @Override
    public Loan getById(String id) {
        Optional<Loan> optionalLoan = loanRepository.findById(id);
        if(optionalLoan.isPresent()){
            return optionalLoan.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("loan with id %s not found", id));
    }

    @Override
    public Page<Loan> getAll(LoanDTO dto, Pageable pageable) {
        Specification<Loan> specification = LoanSpec.getSpecification(dto);
        return loanRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional
    public Loan createTransaction(Loan loan){
        //validasi dateDue < DateBorrow
        LocalDateTime dateDue = loan.getDateDue();
        LocalDateTime dateBorrow = LocalDateTime.now();
        Duration duration = Duration.between(dateBorrow, dateDue);
        if (duration.toDays() < 1 ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "dateDue should not earlier than dateBorrow");
        }

        Integer totalQty = 0;
        for(LoanDetail loanDetail: loan.getLoanDetail()){
            // update stock buku = TAMBAHIN SAAT MERGING.
            Book book = bookService.getBookById(loanDetail.getBook().getId());
            Integer newStock = book.getStock() - loanDetail.getQty();
            if(newStock < 0 ){
                String message = String.format("book stock : %d is less than requested loan", book.getStock());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
            }
            book.setStock(newStock);
            bookService.updateBook(book);

            // save info loanDetail
            loanDetail.setLoan(loan);
            loanDetailService.create(loanDetail);
            totalQty += loanDetail.getQty();
        }
        loan.setTotalQty(totalQty);
        Loan savedloan = loanRepository.save(loan);
        return savedloan;
    }

    @Override
    public Loan update(Loan loan) {
        return loanRepository.save(loan);
    }
}