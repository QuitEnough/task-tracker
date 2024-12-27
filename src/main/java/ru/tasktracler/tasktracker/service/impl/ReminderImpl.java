package ru.tasktracler.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.tasktracler.tasktracker.domain.dto.TaskResponse;
import ru.tasktracler.tasktracker.domain.dto.UserResponse;
import ru.tasktracler.tasktracker.domain.entity.MailType;
import ru.tasktracler.tasktracker.domain.mapper.UserMapper;
import ru.tasktracler.tasktracker.service.MailService;
import ru.tasktracler.tasktracker.service.Reminder;
import ru.tasktracler.tasktracker.service.TaskService;
import ru.tasktracler.tasktracker.service.UserService;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class ReminderImpl implements Reminder {

    private final TaskService taskService;

    private final UserService userService;

    private final MailService mailService;

    private final UserMapper userMapper;

    private final Duration duration = Duration.ofHours(1);

    @Scheduled(cron = "0 * * * * *")
    @Override
    public void remindForTask() {
        List<TaskResponse> tasks = taskService.getAllSoonTasks(duration);
        tasks.forEach(task -> {
            UserResponse user = userService.getTaskAuthor(task.getTaskId());
            Properties properties = new Properties();
            properties.setProperty("task.title", task.getTitle());
            properties.setProperty("task.description", task.getDescription());
            mailService.sendEmail(userMapper.toUser(user), MailType.REMINDER, properties);
        });
    }

}
