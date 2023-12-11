package com.telephonebook.controller;

import com.telephonebook.ValidacaoException;
import com.telephonebook.dto.ContactDto;
import com.telephonebook.service.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/contact")
public class ContactController {
    private final ContactService service;

    @GetMapping
    public List<ContactDto> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> getById(@PathVariable @NotNull Long id) {
        try {
            ContactDto dto = service.getById(id);
            return ResponseEntity.ok(dto);
        } catch (ValidacaoException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity create(@Valid ContactDto dto, @RequestParam(value = "file", required = false) MultipartFile file, UriComponentsBuilder uriBuilder) throws IOException {
        try {
            ContactDto contact = service.create(dto, file);
            URI address = uriBuilder.path("/contact/{id}").buildAndExpand(contact.getId()).toUri();
            return ResponseEntity.created(address).body(contact);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable @NotNull Long id, @Valid ContactDto dto, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
       try {
            ContactDto contact = service.update(id, dto, file);
            return ResponseEntity.ok(contact);
       } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
       }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ContactDto> delete(@PathVariable @NotNull Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
         } catch (ValidacaoException exception) {
             return ResponseEntity.notFound().build();
         }
    }
}