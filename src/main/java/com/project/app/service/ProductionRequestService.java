package com.project.app.service;

import com.project.app.handler.ProductionRequestHandler;
import com.project.app.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductionRequestService {

    ProductionRequestHandler createRequest(ProductionRequestHandler request);

    Page<ProductionRequestHandler> fetchRequests(Pageable pageable);

    ProductionRequestHandler getById(String id);

    List<ProductionRequestHandler> findRequestByStatus(Boolean status);

    ProductionRequestHandler updateRequest(ProductionRequestHandler request);

    void deleteRequest(String id);
}
