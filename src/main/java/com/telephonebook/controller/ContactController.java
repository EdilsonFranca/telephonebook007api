package com.telephonebook.controller;

import com.telephonebook.dto.ContactDto;
import com.telephonebook.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService service;

    @GetMapping
    public List<ContactDto> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> getById(@PathVariable @NotNull Long id) {
        ContactDto dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ContactDto> create(@Valid ContactDto dto, @RequestParam(value = "file", required = false) MultipartFile file, UriComponentsBuilder uriBuilder) throws IOException {
        ContactDto paymentDto = service.create(dto, file);
        URI address = uriBuilder.path("/payments/{id}").buildAndExpand(paymentDto.getId()).toUri();

        return ResponseEntity.created(address).body(paymentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDto> update(@PathVariable @NotNull Long id, @Valid ContactDto dto, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        ContactDto paymentDto = service.update(id, dto, file);
        return ResponseEntity.ok(paymentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ContactDto> delete(@PathVariable @NotNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @PatchMapping("/{id}/confirm")
//    public ResponseEntity<ContactDto> confirmPayment(@PathVariable @NotNull Long id) {
//        ContactDto paymentDto =  service.confirmPayment(id);
//        return ResponseEntity.ok(paymentDto);
//    }

}