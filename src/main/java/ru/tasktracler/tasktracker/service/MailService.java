package ru.tasktracler.tasktracker.service;

import ru.tasktracler.tasktracker.domain.entity.User;
import ru.tasktracler.tasktracker.domain.entity.MailType;

import java.util.Properties;

public interface MailService {

    void sendEmail(User user, MailType type, Properties params);

}
