package com.project.app.repository;

import com.project.app.entity.ReturnBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnBookRepository extends JpaRepository<ReturnBook, String> {
}