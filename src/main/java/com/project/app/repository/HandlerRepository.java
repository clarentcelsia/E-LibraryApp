package com.project.app.repository;

import com.project.app.handler.ResearchPermissionHandler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HandlerRepository extends JpaRepository<ResearchPermissionHandler, String> {
}
