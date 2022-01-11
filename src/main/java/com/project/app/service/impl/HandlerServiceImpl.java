package com.project.app.service.impl;

import com.project.app.entity.Research;
import com.project.app.entity.User;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.files.Files;
import com.project.app.handler.ResearchPermissionHandler;
import com.project.app.repository.HandlerRepository;
import com.project.app.response.PageResponse;
import com.project.app.service.FileService;
import com.project.app.service.HandlerService;
import com.project.app.service.ResearchService;
import com.project.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@Service
public class HandlerServiceImpl implements HandlerService {

    @Autowired
    HandlerRepository repository;

    @Autowired
    FileService fileService;

    @Autowired
    ResearchService researchService;

    @Autowired
    UserService userService;

    @Override
    public ResearchPermissionHandler createRequest(ResearchPermissionHandler handler, MultipartFile file) {
        if (file != null) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                Files files = new Files(filename, file.getBytes(), file.getContentType());
                Files saveFile = fileService.saveFile(files);

                String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/files/" + saveFile.getFileId())
                        .toUriString();

                handler.setResearchFile(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(handler.getAccepted()){
            Research research = new Research();
            research.setResearch(handler.getResearchFile());
            User user = userService.getById(handler.getUser().getId());
            research.setUser(user);
            researchService.saveResearch(research);
        }
        return repository.save(handler);
    }

    @Override
    public Page<ResearchPermissionHandler> fetchRequests(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ResearchPermissionHandler getById(String id) {
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Error: request by id " + id + " not found"));
    }

    @Override
    public ResearchPermissionHandler updateRequest(ResearchPermissionHandler handler, MultipartFile multipartFile) {
        ResearchPermissionHandler id = getById(handler.getRequestId());

        if(multipartFile != null) {
            String file = id.getResearchFile();
            String localhost = file.substring(7);
            String[] urls = localhost.trim().split("/");
            String fileId = urls[urls.length - 1];
            fileService.deleteFileById(fileId);
        }

        id.setComment(handler.getComment());
        if(handler.getAccepted() != null) id.setAccepted(handler.getAccepted());
        return createRequest(id, multipartFile);
    }

    @Override
    public void deleteRequest(String id) {
        ResearchPermissionHandler handler = getById(id);
        repository.delete(handler);
    }
}
