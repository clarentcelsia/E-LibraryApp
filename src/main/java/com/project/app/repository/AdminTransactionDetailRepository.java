package com.project.app.repository;

import com.project.app.entity.AdminTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminTransactionDetailRepository extends JpaRepository<AdminTransactionDetail, String> {
}