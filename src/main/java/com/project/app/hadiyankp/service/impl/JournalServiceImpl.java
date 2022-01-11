package com.project.app.hadiyankp.service.impl;

import com.project.app.hadiyankp.dto.JournalDTO;
import com.project.app.hadiyankp.entity.library.*;
import com.project.app.hadiyankp.exception.NotFoundException;
import com.project.app.hadiyankp.repository.JournalRepository;
import com.project.app.hadiyankp.repository.WriterRepository;
import com.project.app.hadiyankp.request.BookRequest;
import com.project.app.hadiyankp.request.JournalRequest;
import com.project.app.hadiyankp.response.JournalResponse;
import com.project.app.hadiyankp.service.JournalService;
import com.project.app.hadiyankp.service.WriterService;
import com.project.app.hadiyankp.specification.JournalSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class JournalServiceImpl implements JournalService {

    @Autowired
    JournalRepository journalRepository;

    @Autowired
    WriterService writerService;

    @Autowired
    FileService fileService;

    @Override
    public Journal create(JournalRequest request, MultipartFile photo) {
        Files file = fileService.saveMultipartFile(photo);

        String url = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/" + file.getFileId())
                .toUriString();
        request.setFiles(url);

        return mapJournalToDB(request);
    }

    @Override
    public Journal getById(String id) {
        Optional<Journal> journal = journalRepository.findById(id);
        if (journal.isPresent()) {
            return journal.get();
        } else {
            throw new NotFoundException(String.format("Journal with id %s not found", id));
        }
    }

    @Override
    public Page<Journal> listWithPage(Pageable pageable, JournalDTO journalDTO) {
        Specification<Journal> specification = JournalSpecification.getSpecification(journalDTO);
        return journalRepository.findAll(specification, pageable);
    }


    @Override
    public String deleteById(String id) {
        Journal journal = getById(id);
        journal.setDeleted(true);
        journalRepository.save(journal);
        return String.format("Journal with Id %s has been deleted", journal.getId());
    }

    @Override
    public Journal updateById(JournalRequest journal, MultipartFile file) {
        Journal getJournal = getById(journal.getId());

        //handle file
        String files = getJournal.getFiles();

        String removeHttp = files.substring(7);
        String[] strs = removeHttp.trim().split("/");
        String fileId = strs[strs.length - 1];
        fileService.deleteFile(fileId);

        return create(journal, file);
    }

    @Override
    public JournalResponse saveResponse() {
        return null;
    }

    @Override
    public Journal saveJournalToDB(JournalResponse journalResponse) {
//        Journal journal = new Journal(
//                journalResponse.getDoi(),
//                journalResponse.getTitle(),
//                journalResponse.getDescription()
//        );
//        Journal emptyJournal = journalRepository.save(journal);
//        List<Writer> writerList = new ArrayList<>();
//        for (Writer strWriter : journalResponse.getWriters()) {
//            Writer writer = new Writer(strWriter);
//            Writer writerDB = writerRepository.save(writer);
//            emptyJournal.getWriters().add(writerDB);
//        }
//        Journal save = journalRepository.save(emptyJournal);
        return null;
    }

    @Override
    public Journal createJournal(Journal journal, MultipartFile files, Set<Author> authors) {

        return null;
    }

    private Journal mapJournalToDB(JournalRequest request) {
        Journal journal;
        if (request.getId() == null) {
            journal = new Journal();
        } else {
            journal = getById(request.getId());
        }

        journal.setTitle(request.getTitle());
        journal.setDescription(request.getDescription());
        journal.setPublishDate(request.getPublishDate());
        journal.setDoi(request.getDoi());
        journal.setFiles(request.getFiles());

        Journal save = journalRepository.save(journal);

        if(request.getId() == null) {
            for (String writer : request.getWriters()) {
                Writer newWriter = new Writer();
                newWriter.setWriter(writer);
                Writer createdWriter = writerService.saveWriter(newWriter);

                save.getWriters().add(createdWriter);
                createdWriter.getJournals().add(save);
            }
            return journalRepository.save(save);
        }
        return save;
    }

}
