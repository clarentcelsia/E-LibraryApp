package com.project.app.service;

import com.project.app.dto.AdministratorDTO;
import com.project.app.entity.Administrator;
import com.project.app.entity.Role;
import com.project.app.response.AdministratorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface AdministratorService extends UserDetailsService {

    AdministratorResponse create(Administrator administrator, Set<Role> roles);

    Administrator create(Administrator administrator);

    Administrator get(String id);

    Administrator getActiveAdministrator(String id);

    List<Administrator> list();

    Page<Administrator> listWithPage(Pageable pageable, AdministratorDTO administratorDTO);

    Administrator update(Administrator administrator);

    String delete(String id);
}
