package com.telephonebook.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, length = 100)
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Phone> phones;
    @Column(nullable = false, length = 100)
    private String email;
    @Column(length = 100)
    private String avatar;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth_date;
}
