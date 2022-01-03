package com.project.app.hadiyankp.repository;

import com.project.app.hadiyankp.entity.library.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {
}

