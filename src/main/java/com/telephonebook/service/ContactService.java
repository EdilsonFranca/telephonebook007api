package com.telephonebook.service;

import com.telephonebook.model.Contact;
import com.telephonebook.repository.ContactRepository;
import com.telephonebook.dto.ContactDto;
import com.telephonebook.util.FileUploadUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private static String UPLOADED_FOLDER = "src/main/resources/static/photos";

    @Autowired
    private ContactRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ContactDto> findAll() {
        return repository.findAll().stream().map(ContactDto::new).collect(Collectors.toList());
    }

    public ContactDto getById(Long id) {
        Contact Contact = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return modelMapper.map(Contact, ContactDto.class);
    }

    public ContactDto create(ContactDto dto, MultipartFile file) throws IOException {
        Contact Contact = modelMapper.map(dto, Contact.class);

        if (file != null) {
            String fileName = new Date().getTime() + StringUtils.cleanPath(file.getOriginalFilename());
            FileUploadUtil.saveFile(UPLOADED_FOLDER, fileName, file);
            Contact.setAvatar(fileName);
        }

        repository.save(Contact);
        return modelMapper.map(Contact, ContactDto.class);
    }

    public ContactDto update(Long id, ContactDto dto, MultipartFile file) throws IOException {
        Contact Contact = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        Contact.setName(dto.getName());
        Contact.setEmail(dto.getEmail());
        Contact.setPhones(dto.getPhones());
        Contact.setBirth_date(dto.getBirth_date());

        if (file != null) {
            String fileName = new Date().getTime() + StringUtils.cleanPath(file.getOriginalFilename());
            FileUploadUtil.saveFile(UPLOADED_FOLDER, fileName, file);
            Contact.setAvatar(fileName);
        }

        Contact = repository.save(Contact);
        return modelMapper.map(Contact, ContactDto.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
