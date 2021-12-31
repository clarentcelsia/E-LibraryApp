package com.project.app.service;

import com.project.app.dto.SubjectDTO;
import com.project.app.entity.library.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubjectService {

    Subject createSubject(Subject subject);

    Subject getById(String id);

    Page<Subject> listWithPage(Pageable pageable, SubjectDTO subjectDTO);

    Subject updateSubject(Subject subject);

    String deleteSubject(String id);
}
