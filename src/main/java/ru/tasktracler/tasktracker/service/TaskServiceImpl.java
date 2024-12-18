package ru.tasktracler.tasktracker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest) {
        Task task = taskMapper.toTask(taskRequest);
        task.setStatus(Status.TODO);
        taskRepository.save(task);
        taskRepository.assignTask(taskRequest.getUserId(), task.getId());
        return taskMapper.toTaskResponse(task);
    }

    @Override
    public TaskResponse editTask(TaskRequest taskRequest) {
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
        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));
        TaskResponse taskResponse = taskMapper.toTaskResponse(task);
        return taskResponse.getStatus() == Status.DONE;
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public List<TaskResponse> getTasksByUserId(Long userId) {
        return taskMapper.toTaskResponse(
                taskRepository.findTasksByUserId(userId));
    }

    @Override
    public List<TaskResponse> getAllSoonTasks(Duration duration) {
        LocalDateTime now = LocalDateTime.now();
        return taskMapper.toTaskResponse(
                taskRepository.findAllSoonTasks(
                        Timestamp.valueOf(now),
                        Timestamp.valueOf(now.plus(duration))));
    }

}
