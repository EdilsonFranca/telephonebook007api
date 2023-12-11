package com.telephonebook.repository;

import com.telephonebook.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("SELECT c FROM Contact c WHERE MONTH(c.birth_date) = :mes AND DAY(c.birth_date) = :dia")
    List<Contact> findAllByBirthDate(@Param("mes") int mes, @Param("dia") int dia);

}

