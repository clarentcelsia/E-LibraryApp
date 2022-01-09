package com.project.app.service;

import com.project.app.entity.Research;
import com.project.app.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//note: no update research, the research that has been approved by administrator is fixed
public interface ResearchService {

    Research saveResearch(Research research);

    Page<Research> getResearch(Pageable pageable);

    Research getById(String id);

    void deleteResearch(String id);
}
