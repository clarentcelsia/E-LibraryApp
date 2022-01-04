package com.project.app.hadiyankp.service;

import com.project.app.hadiyankp.dto.JournalDTO;
import com.project.app.hadiyankp.dto.PublisherDTO;
import com.project.app.hadiyankp.entity.library.Journal;
import com.project.app.hadiyankp.entity.library.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface JournalService {

    Journal create(Journal journal, MultipartFile photo);
    Journal getById(String id);
    Page<Journal> listWithPage(Pageable pageable, JournalDTO journalDTO);
    String deleteById(String id);
    Journal updateById(Journal journal,MultipartFile photo);
}
