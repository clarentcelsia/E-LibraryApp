package com.project.app.service.impl;

import com.project.app.dto.SubjectDTO;
import com.project.app.entity.library.Subject;
import com.project.app.exception.NotFoundException;
import com.project.app.repository.SubjectRepository;
import com.project.app.service.SubjectService;
import com.project.app.specification.SubjectSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;


    public SubjectServiceImpl() {
    }

    @Override
    public Subject createSubject(Subject subject) {

        return subjectRepository.save(subject);
    }

    @Override
    public Subject getById(String id) {
        Optional<Subject> subject = subjectRepository.findById(id);
        if (subject.isPresent()) {
            return subject.get();
        }else {
            throw new NotFoundException(String.format("Subject with id %s not found", id));
        }
    }

    @Override
    public Page<Subject> listWithPage(Pageable pageable, SubjectDTO subjectDTO) {
        Specification<Subject> specification = SubjectSpecification.getSpecification(subjectDTO);
        return subjectRepository.findAll(specification,pageable);
    }

    @Override
    public Subject updateSubject(Subject subject) {
        getById(subject.getId());
        return subjectRepository.save(subject);
    }

    @Override
    public String deleteSubject(String id) {
        Subject subject = getById(id);
        subjectRepository.delete(subject);
        return String.format("Subject with Id %s has been deleted",subject.getId());
    }
}
