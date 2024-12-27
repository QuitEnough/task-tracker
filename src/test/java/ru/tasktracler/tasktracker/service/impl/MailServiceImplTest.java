package ru.tasktracler.tasktracker.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.tasktracler.tasktracker.config.TestConfig;
import ru.tasktracler.tasktracker.domain.entity.MailType;
import ru.tasktracler.tasktracker.domain.entity.User;

import java.io.IOException;
import java.util.Properties;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
class MailServiceImplTest {

    @MockBean
    private Configuration configuration;

    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private MailServiceImpl mailService;

    @Test
    void sendEmailForRegistration() {
        try {
            String name = "Mike";
            String username = "mike@gmail.com";
            User user = new User();
            user.setName(name);
            user.setEmail(username);
            when(javaMailSender.createMimeMessage())
                    .thenReturn(mock(MimeMessage.class));
            when(configuration.getTemplate("register.ftlh"))
                    .thenReturn(mock(Template.class));
            mailService.sendEmail(user, MailType.REGISTRATION, new Properties());
            verify(configuration).getTemplate("register.ftlh");
            verify(javaMailSender).send(any(MimeMessage.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void sendEmailForRemind() {
        try {
            String name = "Mike";
            String username = "mike@gmail.com";
            User user = new User();
            user.setName(name);
            user.setEmail(username);
            when(javaMailSender.createMimeMessage())
                    .thenReturn(mock(MimeMessage.class));
            when(configuration.getTemplate("reminder.ftlh"))
                    .thenReturn(mock(Template.class));
            mailService.sendEmail(user, MailType.REMINDER, new Properties());
            verify(configuration).getTemplate("reminder.ftlh");
            verify(javaMailSender).send(any(MimeMessage.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}