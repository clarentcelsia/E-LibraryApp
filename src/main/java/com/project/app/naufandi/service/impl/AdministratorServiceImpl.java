package com.project.app.naufandi.service.impl;

import com.project.app.naufandi.dto.AdministratorDTO;
import com.project.app.naufandi.entity.Administrator;
import com.project.app.naufandi.entity.AdministratorDetailImpl;
import com.project.app.naufandi.entity.Role;
import com.project.app.naufandi.exception.NotFoundException;
import com.project.app.naufandi.repository.AdministratorRepository;
import com.project.app.naufandi.response.AdministratorResponse;
import com.project.app.naufandi.service.AdministratorService;
import com.project.app.naufandi.service.RoleService;
import com.project.app.naufandi.specification.AdministratorSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public AdministratorResponse create(Administrator administrator, Set<Role> roles) {
        administrator.setRoles(roles);
        Administrator save = administratorRepository.save(administrator);

        Set<String> strRoles = new HashSet<>();
        for (Role role :save.getRoles()){
            strRoles.add(role.getRole().name());
        }

        return new AdministratorResponse(
                save.getId(),
                save.getIdentityNumber(),
                save.getName(),
                save.getEmail(),
                save.getAddress(),
                save.getPhoneNumber(),
                save.getUsername(),
                save.getPassword(),
                save.getStatus(),
                save.getCreatedAt(),
                save.getUpdatedAt(),
                strRoles
        );
    }

    @Override
    public Administrator create(Administrator administrator) {
        return (Administrator) this.administratorRepository.save(administrator);
    }

    @Override
    public Administrator get(String id) {
        return this.findByIdOrThrowNotFound(id);
    }

    @Override
    public Administrator getActiveAdministrator(String id) {
        return administratorRepository.getActiveAdministrator(id).orElseThrow(() -> new NotFoundException("Administrator not found"));
    }

    @Override
    public List<Administrator> list() {
        return this.administratorRepository.findAll();
    }

    @Override
    public Page<Administrator> listWithPage(Pageable pageable, AdministratorDTO administratorDTO) {
        Specification<Administrator> specification = AdministratorSpecification.getSpecification(administratorDTO);
        return administratorRepository.findAll(specification, pageable);
    }

    @Override
    public Administrator update(Administrator administrator) {
        findByIdOrThrowNotFound(administrator.getId());
        return administratorRepository.save(administrator);
    }

    @Override
    public String delete(String id) {
        Administrator administrator = this.findByIdOrThrowNotFound(id);
        if (administrator.getDeleted()){
            throw new NotFoundException("Administrator not found");
        }
        administrator.setDeleted(true);
        administratorRepository.save(administrator);
        return "Administrator deleted";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Administrator administrator = administratorRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));
        return AdministratorDetailImpl.build(administrator);
    }

    private Administrator findByIdOrThrowNotFound(String id){
        Optional<Administrator> administrator = this.administratorRepository.findById(id);
        if (administrator.isPresent()){
            return administrator.get();
        } else {
            throw new NotFoundException("Administrator not found");
        }
    }
}
