package com.project.app.service.impl;

import com.project.app.entity.Loan;
import com.project.app.entity.LoanDetail;
import com.project.app.repository.LoanRepository;
import com.project.app.service.LoanDetailService;
import com.project.app.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanDetailService loanDetailService;

    @Override
    public Loan create(Loan loan) {
        // tambahin validasi date due lebih dari min sehari dari hari peminjaman.
        // loan.getDateDue();

        // tambahin validasi, user hanya bisa membuat 1 pinjaman aktif
        return loanRepository.save(loan);
    }

    @Override
    public Loan update(Loan loan) {
        getById(loan.getId());
        return loanRepository.save(loan);
    }

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
        return null;
    }

    @Override
    public List<Loan> getAll() {
        return loanRepository.findAll();
    }

    @Override
    @Transactional
    public Loan createTransaction(Loan loan){

        Integer totalQty = 0;
        for(LoanDetail loanDetail: loan.getLoanDetail()){
            // update stock buku

            // save info loanDetail
            loanDetail.setLoan(loan);
            loanDetailService.create(loanDetail);
            totalQty += loanDetail.getQty();
        }
        loan.setTotalQty(totalQty);
        Loan savedloan = loanRepository.save(loan);
        return savedloan;
    }
}
