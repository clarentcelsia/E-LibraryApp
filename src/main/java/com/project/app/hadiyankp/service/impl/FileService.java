package com.project.app.hadiyankp.service.impl;

import com.project.app.hadiyankp.entity.library.Files;
import com.project.app.hadiyankp.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    @Autowired
    FileRepository fileRepository;

    void saveFile(Files files){
        fileRepository.save(files);
    }
//
//    Files get(String id);
}
