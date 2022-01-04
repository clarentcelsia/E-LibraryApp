package com.project.app.service.impl;

import com.project.app.entity.Loan;
import com.project.app.entity.LoanDetail;
import com.project.app.entity.ReturnBook;
import com.project.app.entity.ReturnBookDetail;
import com.project.app.exception.NotFoundException;
import com.project.app.repository.ReturnBookRepository;
import com.project.app.service.LoanService;
import com.project.app.service.ReturnBookDetailService;
import com.project.app.service.ReturnBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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
        Integer totalQty = 0;
        Integer totalPenaltyFee = 0;
        Integer overdueDuration = 0;
        Integer overdueFee = returnBook.getOverdueFee();

        Loan loan = loanService.getById(returnBook.getLoan().getId());

        // validasi, if book is returned then reject this request.
        if (loan.getReturnStatus()){
            throw new RuntimeException("Book has been returned");
        }


        for (int i = 0; i < loan.getLoanDetail().size(); i++) {
            ReturnBookDetail returnBookDetail = returnBook.getReturnBookDetails().get(i);
            LoanDetail loanDetail = loan.getLoanDetail().get(i);

            // Tambahin Validasi, jumlah pengembalian tidak lebih dari jumlah peminjaman.
            Integer lostBook = loanDetail.getQty() - returnBookDetail.getQty();
            if (lostBook < 0){
                throw new RuntimeException("Buku yang dikembalikan lebih dari Jumlah buku yang dipinjam");
            }


            // Tambahkan Stock buku perpus sesuai jumlah pengembalian;


            // isi info returnBookDetail ( isi penalty fee ).
            // TAMBAHIN - logic jumlah hari keterlambatan;
            LocalDateTime dateDue = loan.getDateDue();
//            LocalDateTime dateReturn = LocalDateTime.now();
            LocalDateTime dateReturn = LocalDateTime.of(LocalDate.of(2022,2,1), LocalTime.now());
            Duration duration = Duration.between(dateDue, dateReturn);
            if (duration.toDays() < 1 ){
                overdueDuration = 0;
            } else {
                overdueDuration = Math.toIntExact(duration.toDays());
            }

            // bookPrice nanti ganti sesuai harga buku.
            Integer bookPrice = 50000;
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

    @Override
    public ReturnBook loadReturnBookByLoan(Loan loan) {
        Optional<ReturnBook> returnBook = repository.findByLoan(loan);
        if (returnBook.isPresent()){
            return returnBook.get();
        }
        throw new NotFoundException(String.format("There is no ReturnInfo with Loan Id %s ", loan.getId()));
    }
}
