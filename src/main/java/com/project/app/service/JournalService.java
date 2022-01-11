package com.project.app.service;

import com.project.app.dto.JournalDTO;
import com.project.app.entity.library.Author;
import com.project.app.entity.library.Journal;
import com.project.app.request.JournalRequest;
import com.project.app.response.JournalResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface JournalService {

    Journal create(JournalRequest journal, MultipartFile photo);
    Journal getById(String id);
    Page<Journal> listWithPage(Pageable pageable, JournalDTO journalDTO);
    String deleteById(String id);
    Journal updateById(JournalRequest journal,MultipartFile photo);

    JournalResponse saveResponse();
    Journal saveJournalToDB(JournalResponse journalResponse);
    Journal createJournal (Journal journal, MultipartFile files, Set<Author> authors);
}
