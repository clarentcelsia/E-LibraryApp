package com.project.app.service;

import com.project.app.files.Files;

public interface FileService {

    Files saveFile(Files files);

    Files getDataFileById(String id);

    void deleteFileById(String id);

    Files getFile(String id);
}
