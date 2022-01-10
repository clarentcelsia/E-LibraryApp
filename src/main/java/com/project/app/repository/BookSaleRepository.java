package com.project.app.repository;

import com.project.app.entity.BookSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookSaleRepository extends JpaRepository<BookSale, String> {
}
