package com.project.app.service.impl;

import com.project.app.exception.ResourceNotFoundException;
import com.project.app.files.Files;
import com.project.app.repository.FileRepository;
import com.project.app.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileRepository fileRepository;

    @Override
    public Files saveFile(Files files) {
        return fileRepository.save(files);
    }

    @Override
    public Files getDataFileById(String id) {
        return fileRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Error: file id " + id + " not found"));
    }

    @Override
    public void deleteFileById(String id) {
        Files file = getDataFileById(id);
        fileRepository.delete(file);
    }

    @Override
    public Files getFile(String id){
        return fileRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found")
        );
    }

    public Files saveMultipartFile(MultipartFile file){
        try {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Files files = new Files(filename, file.getBytes(), file.getContentType());
            return saveFile(files);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
