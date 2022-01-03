package com.project.app.hadiyankp.repository;

import com.project.app.hadiyankp.entity.library.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherReporsitory extends JpaRepository<Publisher,String > {
}
