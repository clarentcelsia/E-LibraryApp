package com.project.app.repository;

import com.project.app.entity.library.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JournalRepository extends JpaRepository<Journal,String> {
    Page<Journal> findAll(Specification<Journal> specification, Pageable pageable);

//    Journal save(Journal journal, MultipartFile photo);

//    @Query(value = "SELECT j FROM Journal j WHERE j.isDeleted = false AND j.id = ?1")
//    Optional<Journal> getActive(String id);
}
