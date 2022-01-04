package com.project.app.service.impl;

import com.project.app.entity.Loan;
import com.project.app.entity.LoanDetail;
import com.project.app.entity.ReturnBook;
import com.project.app.entity.ReturnBookDetail;
import com.project.app.repository.ReturnBookRepository;
import com.project.app.service.LoanService;
import com.project.app.service.ReturnBookDetailService;
import com.project.app.service.ReturnBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ReturnBookServiceImpl implements ReturnBookService {
    @Autowired
    private ReturnBookRepository repository;

    @Autowired
    private ReturnBookDetailService returnBookDetailService;

    @Autowired
    private LoanService loanService;

    @Override
    @Transactional
    public ReturnBook createTransaction(ReturnBook returnBook) {
        // validasi, if book is returned then reject this request.

        Integer totalQty = 0;
        Integer totalPenaltyFee = 0;
        Integer overdueFee = returnBook.getOverdueFee();

        Loan loan = loanService.getById(returnBook.getLoan().getId());
        System.out.println(returnBook);


        for (int i = 0; i < returnBook.getReturnBookDetails().size(); i++) {
            ReturnBookDetail returnBookDetail = returnBook.getReturnBookDetails().get(i);
            LoanDetail loanDetail = loan.getLoanDetail().get(i);

            // Tambahkan Stock buku perpus sesuai jumlah pengembalian;



            // isi info returnBookDetail ( isi penalty fee ).

            // returnBookDetail.setReturnBook(returnBook);
            // penaltyFee = overdue_fee * (dateReturn - date borrow ) + BookPrice * lostBook ;

            // TAMBAHIN - logic jumlah hari keterlambatan;
            Integer overdueDuration = 0;
            Integer bookPrice = 50000;
            // Tambahin Validasi, jumlah pengembalian tidak lebih dari jumlah peminjaman.
            Integer lostBook = loanDetail.getQty() - returnBookDetail.getQty();


            Integer penaltyFee = overdueFee * overdueDuration + bookPrice * lostBook;
            returnBookDetail.setPenaltyFee(penaltyFee);

            returnBookDetailService.create(returnBookDetail);

            totalPenaltyFee += penaltyFee;
            totalQty += returnBookDetail.getQty();
        }
        // isi info Loan
        loan.setReturnStatus(true);
        loanService.update(loan);

        // Isi totalQty dan Totalpenalty ke Return
        returnBook.setLoan(loan);
        returnBook.setTotalQty(totalQty);
        returnBook.setTotalPenaltyFee(totalPenaltyFee);

        return repository.save(returnBook);
    }

    @Override
    public List<ReturnBook> getReturnBooks() {
        return repository.findAll();
    }
}
