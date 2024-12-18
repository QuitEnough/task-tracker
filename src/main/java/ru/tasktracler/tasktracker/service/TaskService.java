package ru.tasktracler.tasktracker.service;

import ru.tasktracler.tasktracker.domain.dto.TaskRequest;
import ru.tasktracler.tasktracker.domain.dto.TaskResponse;

import java.time.Duration;
import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskRequest taskRequest);

    TaskResponse editTask(TaskRequest taskRequest);

    boolean isTaskDone(Long taskId);

    void deleteTask(Long taskId);

    List<TaskResponse> getTasksByUserId(Long userId);

    List<TaskResponse> getAllSoonTasks(Duration duration);

}
