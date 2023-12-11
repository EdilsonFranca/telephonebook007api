package com.telephonebook.scheduled;

import com.telephonebook.model.Contact;
import com.telephonebook.model.Email;
import com.telephonebook.service.EmailService;
import com.telephonebook.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Birth {

    private final ContactRepository contactRepository;
    private final EmailService emailService;

    @Scheduled(cron = "0 00 18 * * *")
    public void checkDateOfBirth() {
        List<Contact> contacts_filter = contactRepository.findAllByBirthDate(LocalDate.now().getMonth().getValue(),LocalDate.now().getDayOfMonth())
                                                         .stream()
                                                         .collect(Collectors.toList());
        if (contacts_filter.size() > 0){
            contacts_filter.stream().forEach(e -> {
                Email emailModel = new Email();
                emailModel.setOwnerRef("Sistema de Contatos");

                emailModel.setEmailTo("edilson18martins@gmail.com");
                emailModel.setEmailFrom("appagendadecontatos@gmail.com");

                emailModel.setSubject("Notificão de aviso de Aniversário");
                emailModel.setText("\uD83E\uDD73\uD83E\uDD73\uD83E\uDD73\uD83E\uDD73\uD83E\uDD73 O Contato "+ e.getName()+" está fazendo "+ (LocalDate.now().getYear() - e.getBirth_date().getYear())+" Anos Hoje \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\uD83C\uDF89 ," +" Não esqueça de desejar os Parabéns!  ;)");
                emailService.sendEmail(emailModel);
            });
        }
    }
}
