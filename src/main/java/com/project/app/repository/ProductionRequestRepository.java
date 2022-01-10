package com.project.app.repository;

import com.project.app.handler.ProductionRequestHandler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductionRequestRepository extends JpaRepository<ProductionRequestHandler, String> {

    @Query("SELECT r FROM ProductionRequestHandler r WHERE r.onHandle = ?1")
    Optional<List<ProductionRequestHandler>> getActiveRequest(Boolean onHandle);
}
