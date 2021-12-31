package com.project.app.hadiyankp.service;

import com.project.app.hadiyankp.dto.SubjectDTO;
import com.project.app.hadiyankp.entity.library.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubjectService {

    Subject createSubject(Subject subject);

    Subject getById(String id);

    Page<Subject> listWithPage(Pageable pageable, SubjectDTO subjectDTO);

    Subject updateSubject(Subject subject);

    String deleteSubject(String id);
}
