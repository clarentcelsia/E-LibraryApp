package com.project.app.hadiyankp.service.impl;

import com.project.app.hadiyankp.dto.JournalDTO;
import com.project.app.hadiyankp.entity.library.Files;
import com.project.app.hadiyankp.entity.library.Journal;
import com.project.app.hadiyankp.entity.library.Publisher;
import com.project.app.hadiyankp.exception.NotFoundException;
import com.project.app.hadiyankp.repository.FileRepository;
import com.project.app.hadiyankp.repository.JournalRepository;
import com.project.app.hadiyankp.service.JournalService;
import com.project.app.hadiyankp.specification.JournalSpecification;
import com.project.app.hadiyankp.specification.PublisherSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@Service
public class JournalServiceImpl implements JournalService {
    @Autowired
    JournalRepository journalRepository;

    @Autowired
    FileService fileService;

    @Override
    public Journal create(Journal journal, MultipartFile photo) {
        String filename = StringUtils.cleanPath(photo.getOriginalFilename());

        try {
            Files files = new Files(filename, photo.getBytes(), photo.getContentType());
            fileService.saveFile(files);
            String url= ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/"+ files.getFileId())
                    .toUriString();
            journal.setFiles(url);
            journalRepository.save(journal);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return journalRepository.save(journal);
    }

    @Override
    public Journal getById(String id) {
        Optional<Journal> journal = journalRepository.findById(id);
        if (journal.isPresent()) {
            return journal.get();
        }else {
            throw new NotFoundException(String.format("Journal with id %s not found", id));
        }
    }

    @Override
    public Page<Journal> listWithPage(Pageable pageable, JournalDTO journalDTO) {
        Specification<Journal> specification = JournalSpecification.getSpecification(journalDTO);
        return journalRepository.findAll(specification,pageable);
    }


    @Override
    public String deleteById(String id) {
        return null;
    }

    @Override
    public Journal updateById(Journal journal, MultipartFile photo) {
        return null;
    }

}
