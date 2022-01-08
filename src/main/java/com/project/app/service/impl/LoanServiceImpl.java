package com.project.app.service.impl;

import com.project.app.entity.Book;
import com.project.app.entity.Loan;
import com.project.app.entity.LoanDetail;
import com.project.app.exception.NotFoundException;
import com.project.app.repository.LoanRepository;
import com.project.app.service.LoanDetailService;
import com.project.app.service.LoanService;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanDetailService loanDetailService;

    @Override
    public String deleteById(String id) {
        Loan loan = getById(id);
        loanRepository.delete(loan);
        return String.format("Loan with %s is deleted", id);
    }

    @Override
    public Loan getById(String id) {
        Optional<Loan> optionalLoan = loanRepository.findById(id);
        if(optionalLoan.isPresent()){
            return optionalLoan.get();
        }
        // tambahin exception
        throw new NotFoundException(String.format("Loan with Id %s not found", id));
    }

    @Override
    public List<Loan> getAll() {
        return loanRepository.findAll();
    }

    @Override
    @Transactional
    public Loan createTransaction(Loan loan){
        //validasi dateDue < DateBorrow
        LocalDateTime dateDue = loan.getDateDue();
        LocalDateTime dateBorrow = LocalDateTime.now();
        Duration duration = Duration.between(dateBorrow, dateDue);
        if (duration.toDays() < 1 ){
            throw new RuntimeException("Masukkan tanggal pengembalian dengan durasi minimal 1 hari dari tanggal pinjam");
        }

        Integer totalQty = 0;
        for(LoanDetail loanDetail: loan.getLoanDetail()){
            // update stock buku = TAMBAHIN SAAT MERGING.
//            Book book = loanDetail.getBook();
//            Integer newStock = book.getStock() - loanDetail.getQty();
//            if(newStock < 0 ){
//                String message = String.format("stock buku kurang, tersedia %d buku", book.getStock());
//                throw new RuntimeException(message);
//            }

//            book.setStock(newStock);
            // bookservice.update(book)


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
