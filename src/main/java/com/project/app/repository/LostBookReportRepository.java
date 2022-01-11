package com.project.app.repository;

import com.project.app.entity.LostBookReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LostBookReportRepository extends JpaRepository<LostBookReport, String> {
    Page<LostBookReport> findAll(Specification<LostBookReport> specification, Pageable pageable);
}