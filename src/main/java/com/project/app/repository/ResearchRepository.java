package com.project.app.repository;

import com.project.app.entity.Research;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearchRepository extends JpaRepository<Research, String> {
}
