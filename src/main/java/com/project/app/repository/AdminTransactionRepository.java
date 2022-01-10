package com.project.app.repository;

import com.project.app.entity.AdminTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminTransactionRepository extends JpaRepository<AdminTransaction, String> {
}
