package ru.tasktracler.tasktracker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest) {
        log.debug("[TaskService] Saving task with details: {}", taskRequest);

        Task task = taskMapper.toTask(taskRequest);
        task.setStatus(Status.TODO);
        taskRepository.save(task);
        // TODO: кинуть эксепшн, посмотреть, что save сделает rollback
        taskRepository.assignTask(taskRequest.getUserId(), task.getId());
        return taskMapper.toTaskResponse(task);
    }

    @Override
    public TaskResponse editTask(TaskRequest taskRequest) {
        log.debug("[TaskService] Updating task with details: {}", taskRequest);

        Task editedTask = Task
                .builder()
                .id(taskRequest.getTaskId())
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .status(taskRequest.getStatus() == null ? Status.TODO : taskRequest.getStatus())
                .expirationDate(taskRequest.getExpirationDate())
                .build();
        taskRepository.save(editedTask);
        return taskMapper.toTaskResponse(editedTask);
    }

    @Override
    public boolean isTaskDone(Long taskId) {
        log.debug("[TaskService] Checking if task with id: {} - is done", taskId);

        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));
        TaskResponse taskResponse = taskMapper.toTaskResponse(task);
        return taskResponse.getStatus() == Status.DONE;
    }

    @Override
    public void deleteTask(Long taskId) {
        log.debug("[TaskService] Deleting task with id: {}", taskId);
        taskRepository.deleteById(taskId);
    }

    @Override
    public List<TaskResponse> getTasksByUserId(Long userId) {
        log.debug("[TaskService] Getting task for user with id: {}", userId);
        return taskMapper.toTaskResponse(
                taskRepository.findTasksByUserId(userId));
    }

    @Override
    public List<TaskResponse> getAllSoonTasks(Duration duration) {
        log.debug("[TaskService] Getting all tasks with soon expiration");

        LocalDateTime now = LocalDateTime.now();
        return taskMapper.toTaskResponse(
                taskRepository.findAllSoonTasks(
                        Timestamp.valueOf(now),
                        Timestamp.valueOf(now.plus(duration))));
    }

}
