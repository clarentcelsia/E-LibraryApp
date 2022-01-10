package com.project.app.hadiyankp.repository;

import com.project.app.hadiyankp.entity.library.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriterRepository extends JpaRepository<Writer,String> {
}
