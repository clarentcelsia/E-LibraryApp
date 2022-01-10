package com.project.app.repository;

import com.project.app.entity.ProductionBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionBookRepository extends JpaRepository<ProductionBook, String> {
}
