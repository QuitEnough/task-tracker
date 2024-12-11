package ru.tasktracler.tasktracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tasktracler.tasktracker.domain.dto.TaskRequest;
import ru.tasktracler.tasktracker.domain.dto.TaskResponse;
import ru.tasktracler.tasktracker.domain.entity.Task;
import ru.tasktracler.tasktracker.repository.TaskRepository;

import java.time.Duration;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Long createTask(TaskRequest taskRequest) {
        return null;
    }

    @Override
    public TaskResponse editTask(TaskRequest taskRequest) {
        return null;
    }

    @Override
    public boolean isTaskDone(Long taskId) {
        return false;
    }

    @Override
    public void deleteTask(Long taskId) {

    }

    @Override
    public List<Task> getTasksByUserId(Long taskId) {
        return null;
    }

    @Override
    public List<Task> getAllSoonTasks(Duration duration) {
        return null;
    }

}
