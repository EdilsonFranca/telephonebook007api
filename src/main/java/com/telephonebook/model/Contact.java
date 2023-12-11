package com.telephonebook.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Setter
    @Column(length = 100)
    private String avatar;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth_date;
}
