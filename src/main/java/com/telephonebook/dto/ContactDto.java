package com.telephonebook.dto;

import com.telephonebook.model.Contact;
import com.telephonebook.model.Phone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {

    private long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    @Size(max = 100)
    private String email;

    private List<Phone> phones;

    @Size(max = 100)
    private String avatar;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth_date;
    public ContactDto(Contact contact) {
        this.id         = contact.getId();
        this.name       = contact.getName();
        this.email      = contact.getEmail();
        this.avatar     = contact.getAvatar();
        this.phones     = contact.getPhones();
        this.birth_date = contact.getBirth_date();
    }
}
