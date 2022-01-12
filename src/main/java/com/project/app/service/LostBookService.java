package com.project.app.service;

import com.project.app.dto.LostBookDTO;
import com.project.app.entity.LostBookReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LostBookService {
    LostBookReport create(LostBookReport lostBookReport);
    Page<LostBookReport> getAll(LostBookDTO dto, Pageable pageable);
}
