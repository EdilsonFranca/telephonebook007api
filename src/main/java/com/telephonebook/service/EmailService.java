package com.telephonebook.service;

import com.telephonebook.enums.StatusEmail;
import com.telephonebook.model.Contact;
import com.telephonebook.model.EmailModel;
import com.telephonebook.repository.ContactRepository;
import com.telephonebook.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private ContactRepository contactRepository;

    @Scheduled(cron = "0 25 18 * * *")
    public void checkDateOfBirth() {
        List<Contact> contacts_filter = contactRepository.findAll()
                    .stream()
                    .filter(c -> c.getBirth_date() != null && c.getBirth_date().getDayOfMonth() == LocalDate.now().getDayOfMonth() && c.getBirth_date().getMonth().getValue() == LocalDate.now().getMonth().getValue())
                    .collect(Collectors.toList());

        if (contacts_filter.size() > 0){
            contacts_filter.stream().forEach(e -> {
                EmailModel emailModel = new EmailModel();
                emailModel.setOwnerRef("Sistema de Contatos");
                emailModel.setEmailTo("******************");
                emailModel.setEmailFrom("******************");
                emailModel.setSubject("Notificão de aviso de Aniversário");
                emailModel.setText("\uD83E\uDD73\uD83E\uDD73\uD83E\uDD73\uD83E\uDD73\uD83E\uDD73 O Contato "+ e.getName()+" está fazendo "+ (LocalDate.now().getYear() - e.getBirth_date().getYear())+" Anos Hoje \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\uD83C\uDF89 ," +" Não esqueça de desejar os Parabéns!  ;)");
                sendEmail(emailModel);
            });
        }
    }

    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e){
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return emailRepository.save(emailModel);
        }
    }
}