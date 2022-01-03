package com.project.app.controller;

import com.project.app.entity.Loan;
import com.project.app.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @GetMapping
    public List<Loan> getAllLoan(){
        return loanService.getAll();
    }

    @GetMapping("/{loanId}")
    public Loan getOneLoan(@PathVariable("loanId") String id){
        return loanService.getById(id);
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
}
