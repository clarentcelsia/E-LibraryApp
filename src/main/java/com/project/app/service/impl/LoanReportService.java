package com.project.app.service.impl;

import com.project.app.entity.Loan;
import com.project.app.entity.LoanDetail;
import com.project.app.jasperObject.LoanReport;
import com.project.app.repository.LoanRepository;
import com.project.app.repository.ReturnBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanReportService {
    @Autowired
    LoanRepository repository;

    public List<LoanReport> createReport(){
        List<Loan> loans = repository.findAll();

        List<LoanReport> loanReports = new ArrayList<>();
        for (Loan loan : loans){
            if (!loan.getReturnStatus()){
                continue;
            }

            for(LoanDetail loanDetail : loan.getLoanDetail()){
                String dateBorrow = loan.getDateBorrow().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String dateReturn;
                if (!loan.getReturnStatus()) dateReturn = "";
                else dateReturn = loan.getReturnBook().getDateReturn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                // fill loan reports - fill book code when merging
                LoanReport loanReport = new LoanReport();
                loanReport.setBookName(loanDetail.getBook().getTitle());
                loanReport.setBookCode(loanDetail.getBook().getBookCode());
                loanReport.setDateReturn(dateReturn);
                loanReport.setDateBorrow(dateBorrow);
                loanReport.setUserName(loan.getUser().getName());

                // add reports
                loanReports.add(loanReport);
            }
        }
        return loanReports;
    }

}

