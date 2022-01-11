package com.project.app.naufandi.controller;

import com.project.app.naufandi.dto.AdministratorDTO;
import com.project.app.naufandi.entity.Administrator;
import com.project.app.naufandi.response.PageResponse;
import com.project.app.naufandi.service.AdministratorService;
import com.project.app.naufandi.util.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @PostMapping
    public ResponseEntity<WebResponse<Administrator>> createAdmin(@RequestBody Administrator request){
        Administrator administrator = this.administratorService.create(request);
        WebResponse<Administrator> webResponse = new WebResponse<>("Successfully created new adminstrator", administrator);
        return ResponseEntity.status(HttpStatus.CREATED).body(webResponse);
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<WebResponse<Administrator>> getById(@PathVariable("adminId") String id){
        Administrator administrator = administratorService.getActiveAdministrator(id);
        WebResponse<Administrator> response = new WebResponse<>(String.format("Administrator with id %s found", id), administrator);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<WebResponse<PageResponse<Administrator>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "identitynumber", required = false) String identityNumber){
        Pageable pageable = PageRequest.of(page, size);
        AdministratorDTO administratorDTO = new AdministratorDTO(name, identityNumber);

        Page<Administrator> administrators = this.administratorService.listWithPage(pageable, administratorDTO);

        PageResponse<Administrator> pageResponse = new PageResponse<>(
                administrators.getContent(),
                administrators.getTotalElements(),
                administrators.getTotalPages(),
                page,
                size
        );
        WebResponse<PageResponse<Administrator>> response = new WebResponse<>("Successfully get data administrator", pageResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<WebResponse<Administrator>> updateAdminById(@RequestBody Administrator request){
        Administrator update = this.administratorService.update(request);
        WebResponse<Administrator> webResponse = new WebResponse<>("Successfully update administrator", update);
        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<WebResponse<String>> deleteAdminById(@PathVariable("adminId") String id){
        String message = this.administratorService.delete(id);
        WebResponse<String> webResponse = new WebResponse<>(message, id);
        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }
}
