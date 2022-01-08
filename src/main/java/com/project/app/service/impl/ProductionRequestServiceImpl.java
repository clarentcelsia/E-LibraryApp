package com.project.app.service.impl;

import com.project.app.exception.ResourceNotFoundException;
import com.project.app.handler.ProductionRequestHandler;
import com.project.app.repository.ProductionRequestRepository;
import com.project.app.response.PageResponse;
import com.project.app.service.ProductionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionRequestServiceImpl implements ProductionRequestService {

    @Autowired
    ProductionRequestRepository repository;

    @Override
    public ProductionRequestHandler createRequest(ProductionRequestHandler request) {
        return repository.save(request);
    }

    @Override
    public PageResponse<ProductionRequestHandler> fetchRequests(Pageable pageable) {
        Page<ProductionRequestHandler> page = repository.findAll(pageable);
        PageResponse<ProductionRequestHandler> response = new PageResponse<>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages()
        );
        return response;
    }

    @Override
    public ProductionRequestHandler getById(String id) {
        return repository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Error: data by id " + id + " not found"));
    }

    @Override
    public List<ProductionRequestHandler> findRequestByStatus(Boolean status) {
        return repository.getActiveRequest(status).orElseThrow(()->
                new ResourceNotFoundException("Error: data not found."));
    }

    @Override
    public ProductionRequestHandler updateRequest(ProductionRequestHandler request) {
        ProductionRequestHandler handler = getById(request.getProductionRequestHandlerId());
        if(request.getOnHandle() != null) handler.setOnHandle(request.getOnHandle());
        return createRequest(handler);
    }

    @Override
    public void deleteRequest(String id) {
        ProductionRequestHandler requestHandler = getById(id);
        repository.delete(requestHandler);
    }
}
