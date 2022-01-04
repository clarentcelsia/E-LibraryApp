package com.project.app.repository;

import com.project.app.entity.ReturnBookDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnBookDetailRepository extends JpaRepository<ReturnBookDetail, String> {
}