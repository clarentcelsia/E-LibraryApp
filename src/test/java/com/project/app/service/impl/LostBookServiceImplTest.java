//package com.project.app.service.impl;
//
//import com.project.app.dto.LostBookDTO;
//import com.project.app.entity.Book;
//import com.project.app.entity.LostBookReport;
//import com.project.app.entity.User;
//import com.project.app.repository.LostBookReportRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.*;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith(MockitoExtension.class)
//class LostBookServiceImplTest {
//    @InjectMocks
//    LostBookServiceImpl service;
//
//    @Mock
//    LostBookReportRepository repository;
//
//    private LostBookReport inputLostBook;
//    private LostBookReport outputLostBook;
//    private Book book;
//    private User jannes;
//    @BeforeEach
//    public void setup(){
//        jannes = new User("user-1", "jannes");
//        book = new Book("book-1", "buku mtk", 100);
//
//        inputLostBook = new LostBookReport(null, jannes, book, 2, LocalDateTime.now());
//        outputLostBook = new LostBookReport("lost-book-1", jannes, book, 2, LocalDateTime.now());
//    }
//
//    @Test
//    public void getAll_ShouldReturn_AllPagedLostBookReport(){
//        List<LostBookReport> lostBookReports = new ArrayList<>();
//        lostBookReports.add(outputLostBook);
//
//        LostBookDTO dto = new LostBookDTO("book-1", null);
//        Sort sort = Sort.by(Sort.Direction.ASC, "dateLost");
//        Pageable pageable = PageRequest.of(0,1, sort);
//
//        Page<LostBookReport> lostPage = new PageImpl<>(lostBookReports, pageable, lostBookReports.size());
//
//        // actual
//        Mockito.when(repository.findAll(Mockito.any(Specification.class),Mockito.any(Pageable.class))).thenReturn(lostPage);
//
//        // expected
//        Page<LostBookReport> pagedLosts = service.getAll(dto, pageable);
//        assertEquals(pagedLosts.getTotalElements(), 1);
//    }
//
//    @Test
//    public void create_Should_AddLostBooktoCollection(){
//        Mockito.when(repository.save(inputLostBook)).thenReturn(outputLostBook);
//
//        LostBookReport savedLost = service.create(inputLostBook);
//        List<LostBookReport> lostBooks = new ArrayList<>();
//        lostBooks.add(savedLost);
//
//        // actual
//        Mockito.when(repository.findAll()).thenReturn(lostBooks);
//        assertEquals(repository.findAll().size(), 1);
//    }
//}