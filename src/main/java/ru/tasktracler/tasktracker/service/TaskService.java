package ru.tasktracler.tasktracker.service;

import ru.tasktracler.tasktracker.domain.dto.TaskRequest;
import ru.tasktracler.tasktracker.domain.dto.TaskResponse;
import ru.tasktracler.tasktracker.domain.entity.Task;

import java.time.Duration;
import java.util.List;

public interface TaskService {

    Long createTask(TaskRequest taskRequest);

    TaskResponse editTask(TaskRequest taskRequest);

    boolean isTaskDone(Long taskId);

    void deleteTask(Long taskId);

    List<Task> getTasksByUserId(Long taskId);

    List<Task> getAllSoonTasks(Duration duration);

}
