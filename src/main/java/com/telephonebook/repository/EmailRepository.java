package com.telephonebook.repository;

import com.telephonebook.model.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailRepository extends JpaRepository<EmailModel, UUID> {}