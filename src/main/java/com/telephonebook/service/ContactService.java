package com.telephonebook.service;

import com.telephonebook.dto.ContactDto;
import com.telephonebook.model.Contact;
import com.telephonebook.repository.ContactRepository;
import com.telephonebook.util.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ContactService {

    private static String UPLOADED_FOLDER = "src/main/resources/static/photos";

    private final ContactRepository repository;

    private final ModelMapper modelMapper;

    public List<ContactDto> findAll() {
        return repository.findAll()
                         .stream()
                         .map(ContactDto::new)
                         .toList();
    }

    public ContactDto getById(Long id) {
        Contact contact = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return modelMapper.map(contact, ContactDto.class);
    }

    public ContactDto create(ContactDto dto, MultipartFile file) throws IOException {
        Contact contact = modelMapper.map(dto, Contact.class);

        if (file != null)
            salvaAvatar(file, contact);

        Contact contactSave = repository.save(contact);
        return modelMapper.map(contactSave, ContactDto.class);
    }

    public ContactDto update(Long id, ContactDto dto, MultipartFile file) throws IOException {
        Contact contact = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        contact.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phones(dto.getPhones())
                .birth_date(dto.getBirth_date())
                .build();

        if (file != null)
            salvaAvatar(file, contact);

        contact = repository.save(contact);

        return modelMapper.map(contact, ContactDto.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private static void salvaAvatar(MultipartFile file, Contact contact) throws IOException {
        String fileName = new Date().getTime() + StringUtils.cleanPath(file.getOriginalFilename());
        FileUploadUtil.saveFile(UPLOADED_FOLDER, fileName, file);
        contact.setAvatar(fileName);
    }
}
