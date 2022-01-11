package com.project.app.service.impl;

import com.project.app.dto.TypeDTO;
import com.project.app.entity.library.Type;
import com.project.app.exception.NotFoundException;
import com.project.app.repository.TypeRepository;
import com.project.app.service.TypeService;
import com.project.app.specification.TypeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;

    @Override
    public Type createType(Type type) {

        return typeRepository.save(type);
    }

    @Override
    public Type getById(String id) {
        Optional<Type> type = typeRepository.findById(id);
        if (type.isPresent()) {
            return type.get();
        }else {
            throw new NotFoundException(String.format("Subject with id %s not found", id));
        }
    }

    @Override
    public Page<Type> listWithPage(Pageable pageable, TypeDTO typeDTO) {
        Specification<Type> specification = TypeSpecification.getSpecification(typeDTO);
        return typeRepository.findAll(specification,pageable);
    }

    @Override
    public Type updateType(Type type) {
        getById(type.getId());
        return typeRepository.save(type);
    }

    @Override
    public String deleteType(String id) {
        Type type = getById(id);
        typeRepository.delete(type);
        return String.format("Subject with Id %s has been deleted",type.getId());
    }
}
