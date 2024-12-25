package ru.tasktracler.tasktracker.controller;

import org.junit.jupiter.api.Test;
import ru.tasktracler.tasktracker.domain.dto.TaskRequest;
import ru.tasktracler.tasktracker.domain.dto.TaskResponse;
import ru.tasktracler.tasktracker.domain.entity.Status;
import ru.tasktracler.tasktracker.repository.TaskRepository;
import ru.tasktracler.tasktracker.service.TaskServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    private final TaskServiceImpl taskService = mock(TaskServiceImpl.class);

    private final TaskController taskController = new TaskController(taskService);

    @Test
    void createTask() {
        TaskRequest taskRequest = TaskRequest
                .builder()
                .taskId(1L)
                .title("title")
                .description("desc")
                .status(Status.TODO)
                .expirationDate(LocalDateTime.now())
                .userId(1L)
                .build();

        TaskResponse taskResponse = TaskResponse
                .builder()
                .taskId(1L)
                .title("title")
                .description("desc")
                .status(Status.TODO)
                .expirationDate(LocalDateTime.now())
                .build();

        when(taskService.createTask(taskRequest))
                .thenReturn(taskResponse);

        TaskResponse testResponse = taskController.createTask(taskRequest);

        assertNotNull(testResponse);
        assertEquals(taskResponse.getTaskId(), testResponse.getTaskId());
        assertEquals(taskResponse.getTitle(), testResponse.getTitle());
        assertEquals(taskResponse.getDescription(), testResponse.getDescription());
        assertEquals(taskResponse.getStatus(), testResponse.getStatus());
        assertEquals(taskResponse.getExpirationDate(), testResponse.getExpirationDate());
    }

    @Test
    void getTasksByUserId() {
        Long userId = 1L;
        List<TaskResponse> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tasks.add(new TaskResponse());
        }

        when(taskService.getTasksByUserId(userId))
                .thenReturn(tasks);

        List<TaskResponse> testTasks = taskController.getTasksByUserId(userId);
        assertNotNull(testTasks);
    }

    @Test
    void updateTask() {
        TaskRequest taskRequest = TaskRequest
                .builder()
                .taskId(1L)
                .title("title")
                .description("desc")
                .status(Status.TODO)
                .expirationDate(LocalDateTime.now())
                .userId(1L)
                .build();

        TaskResponse taskResponse = TaskResponse
                .builder()
                .taskId(1L)
                .title("title")
                .description("desc")
                .status(Status.TODO)
                .expirationDate(LocalDateTime.now())
                .build();

        when(taskService.editTask(taskRequest))
                .thenReturn(taskResponse);

        TaskResponse testResponse = taskController.updateTask(taskRequest);

        assertNotNull(testResponse);
        assertEquals(taskResponse.getTaskId(), testResponse.getTaskId());
        assertEquals(taskResponse.getTitle(), testResponse.getTitle());
        assertEquals(taskResponse.getDescription(), testResponse.getDescription());
        assertEquals(taskResponse.getStatus(), testResponse.getStatus());
        assertEquals(taskResponse.getExpirationDate(), testResponse.getExpirationDate());
    }

    @Test
    void deleteTask() {
        Long taskId = 1L;
        taskController.deleteTask(taskId);
        TaskRepository taskRepository = mock(TaskRepository.class);
        verify(taskService).deleteTask(taskId);
    }

}