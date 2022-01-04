package com.project.app.hadiyankp.repository;

import com.project.app.hadiyankp.entity.library.Author;
import com.project.app.hadiyankp.entity.library.Journal;
import com.project.app.hadiyankp.entity.library.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface JournalRepository extends JpaRepository<Journal,String> {
    Page<Journal> findAll(Specification<Journal> specification, Pageable pageable);

//    Journal save(Journal journal, MultipartFile photo);

//    @Query(value = "SELECT j FROM Journal j WHERE j.isDeleted = false AND j.id = ?1")
//    Optional<Journal> getActive(String id);
}
