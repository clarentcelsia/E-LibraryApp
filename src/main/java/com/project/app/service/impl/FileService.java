package com.project.app.service.impl;

import com.project.app.exception.ResourceNotFoundException;
import com.project.app.files.Files;
import com.project.app.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import static com.project.app.utils.Utility.RESPONSE_NOT_FOUND;

@Service
public class FileService {

    @Autowired
    FileRepository fileRepository;

    public void saveFile(Files files) {
        fileRepository.save(files);
    }

    public Files getFileById(String id) {
        return fileRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(RESPONSE_NOT_FOUND, id)));
    }

    public void deleteFile(String id) {
        Files file = getFileById(id);
        fileRepository.delete(file);
    }

    public Files saveMultipartFile(MultipartFile file) {
        try {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Files files = new Files(filename, file.getBytes(), file.getContentType());
            return fileRepository.save(files);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
