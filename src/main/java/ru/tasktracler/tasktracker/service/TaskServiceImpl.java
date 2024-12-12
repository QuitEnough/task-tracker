package ru.tasktracler.tasktracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tasktracler.tasktracker.domain.dto.TaskRequest;
import ru.tasktracler.tasktracker.domain.dto.TaskResponse;
import ru.tasktracler.tasktracker.domain.entity.Status;
import ru.tasktracler.tasktracker.domain.entity.Task;
import ru.tasktracler.tasktracker.domain.mapper.TaskMapper;
import ru.tasktracler.tasktracker.exception.ResourceNotFoundException;
import ru.tasktracler.tasktracker.repository.TaskRepository;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskResponse createTask(TaskRequest taskRequest) {
        Task task = taskMapper.toTask(taskRequest);
        task.setStatus(Status.TODO);
        taskRepository.save(task);
        return taskMapper.toTaskResponse(task);
    }

    @Override
    public TaskResponse getById(Long taskId) {
        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));
        return taskMapper.toTaskResponse(task);
    }

    @Override
    public TaskResponse editTask(TaskRequest taskRequest) {
        Status status;
        if (taskRequest.getStatus() == null) {
            status = Status.TODO;
        } else {
            status = taskRequest.getStatus();
        }
        Task editedTask = Task
                .builder()
                .id(taskRequest.getTaskId())
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .status(status)
                .expirationDate(taskRequest.getExpirationDate())
                .userId(taskRequest.getUserId())
                .build();
        taskRepository.save(editedTask);
        return taskMapper.toTaskResponse(editedTask);
    }

    @Override
    public boolean isTaskDone(Long taskId) {
        TaskResponse taskResponse = getById(taskId);
        return taskResponse.getStatus() == Status.DONE;
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public List<TaskResponse> getTasksByUserId(Long userId) {
        return taskRepository
                .findTasksByUserId(userId)
                .stream()
                .map(taskMapper::toTaskResponse)
                .toList();
    }

    @Override
    public List<TaskResponse> getAllSoonTasks(Duration duration) {
        LocalDateTime now = LocalDateTime.now();
        return taskRepository
                .findAllSoonTasks(Timestamp.valueOf(now), Timestamp.valueOf(now.plus(duration)))
                .stream()
                .map(taskMapper::toTaskResponse)
                .toList();
    }

}
