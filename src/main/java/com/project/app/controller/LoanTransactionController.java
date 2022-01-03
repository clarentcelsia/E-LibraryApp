package com.project.app.controller;

import com.project.app.entity.Loan;
import com.project.app.entity.LoanDetail;
import com.project.app.service.LoanDetailService;
import com.project.app.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/detail")
public class LoanTransactionController {
    @Autowired
    private LoanDetailService loanDetailService;

    @PostMapping
    public LoanDetail createLoan(@RequestBody LoanDetail loanDetail){
        return loanDetailService.create(loanDetail);
    }

}
