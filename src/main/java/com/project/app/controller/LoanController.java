package com.project.app.controller;

import com.project.app.entity.Loan;
import com.project.app.entity.ReturnBook;
import com.project.app.service.LoanService;
import com.project.app.service.ReturnBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @Autowired
    private ReturnBookService returnBookService;

    @GetMapping
    public List<Loan> getAllLoan(){
        return loanService.getAll();
    }

    @GetMapping("/{loanId}")
    public Loan getOneLoan(@PathVariable("loanId") String id){
        return loanService.getById(id);
    }

    @GetMapping("/{loanId}/status")
    public ReturnBook getReturnBookStatus(@PathVariable("loanId") String id){
        Loan loan = loanService.getById(id);
        return returnBookService.loadReturnBookByLoan(loan);
    }

    @PutMapping
    public Loan updateLoan(@RequestBody Loan loan){
        return loanService.update(loan);
    }

    @PostMapping
    public Loan createLoan(@RequestBody Loan loan){
        return loanService.create(loan);
    }

    @DeleteMapping("/{loanId}")
    public String deleteLoan(@PathVariable("loanId") String id){
        return loanService.deleteById(id);
    }

    @PostMapping("/transaction")
    public Loan createTransactionLoan(@RequestBody Loan loan){
        return loanService.createTransaction(loan);
    }
}
