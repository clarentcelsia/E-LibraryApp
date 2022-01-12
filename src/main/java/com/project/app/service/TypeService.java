package com.project.app.service;

import com.project.app.dto.TypeDTO;
import com.project.app.entity.library.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TypeService {

    Type createType(Type type);

    Type getById(String id);

    Page<Type> listWithPage(Pageable pageable, TypeDTO typeDTO);

    Type updateType(Type type);

    String deleteType(String id);
}
