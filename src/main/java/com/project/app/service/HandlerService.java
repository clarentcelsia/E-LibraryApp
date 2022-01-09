package com.project.app.service;

import com.project.app.entity.Research;
import com.project.app.handler.ResearchPermissionHandler;
import com.project.app.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HandlerService {

    ResearchPermissionHandler createRequest(ResearchPermissionHandler handler, MultipartFile file);

    Page<ResearchPermissionHandler> fetchRequests(Pageable pageable);

    ResearchPermissionHandler getById(String id);

    ResearchPermissionHandler updateRequest(ResearchPermissionHandler handler, MultipartFile file);

    void deleteRequest(String id);
}
