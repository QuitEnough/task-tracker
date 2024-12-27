package ru.tasktracler.tasktracker.service.impl;

import org.junit.jupiter.api.Test;
import ru.tasktracler.tasktracker.domain.dto.TaskRequest;
import ru.tasktracler.tasktracker.domain.dto.TaskResponse;
import ru.tasktracler.tasktracker.domain.entity.Status;
import ru.tasktracler.tasktracker.domain.entity.Task;
import ru.tasktracler.tasktracker.domain.mapper.TaskMapper;
import ru.tasktracler.tasktracker.repository.TaskRepository;
import ru.tasktracler.tasktracker.service.impl.TaskServiceImpl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    private final TaskRepository taskRepository = mock(TaskRepository.class);

    private final TaskMapper taskMapper = mock(TaskMapper.class);

    private final TaskServiceImpl taskService = new TaskServiceImpl(taskRepository, taskMapper);

//    @Test
//    void createTask() {
//        Status status = Status.TODO;
//        try (MockedStatic<Status> mockedStatus = mockStatic(Status.class)) {
//            TaskRequest taskRequest = TaskRequest
//                    .builder()
//                    .taskId(1L)
//                    .title("task")
//                    .description("desc")
//                    .status(status)
//                    .expirationDate(LocalDateTime.now())
//                    .userId(1L)
//                    .build();
//
//            mockedStatus.when(Task::setStatus).thenReturn(status);
//
//            taskService.createTask(taskRequest);
//            verify(taskRepository).save(any());
//        }
//
//    }

    @Test
    void createTask() {
        Long userId = 1L;
        Long taskId = 1L;
        Task task = new Task();
        doAnswer(invocation -> {
            Task createdTask = invocation.getArgument(0);
            createdTask.setId(taskId);
            createdTask.setStatus(Status.TODO);
            return createdTask;
        }).when(taskRepository).save(task);

        TaskResponse testTask = taskService.createTask(taskMapper.toTaskRequest(task));
        verify(taskRepository).save(task);
        assertNotNull(testTask.getTaskId());
        verify(taskRepository).assignTask(userId, task.getId());
    }

    @Test
    void editTask() {
        Long id = 1L;
        Task task = new Task();
        task.setId(id);
        task.setStatus(Status.DONE);

        when(taskRepository.findById(task.getId()))
                .thenReturn(Optional.of(task));

        TaskResponse testTask = taskService.editTask(taskMapper.toTaskRequest(task));
        verify(taskRepository).save(task);
        assertEquals(taskMapper.toTaskResponse(task), testTask);
        assertEquals(task.getTitle(), testTask.getTitle());
        assertEquals(task.getDescription(), testTask.getDescription());
        assertEquals(task.getStatus(), testTask.getStatus());

//        TaskRequest taskRequest = TaskRequest
//                .builder()
//                .taskId(1L)
//                .title("task")
//                .description("desc")
//                .status(Status.TODO)
//                .expirationDate(LocalDateTime.now())
//                .userId(1L)
//                .build();
//
//        taskService.editTask(taskRequest);
//        verify(taskRepository).save(any());
    }

    @Test
    void editTaskWithEmptyStatus() {
        TaskRequest taskRequest = TaskRequest
                .builder()
                .taskId(1L)
                .title("task")
                .description("desc")
                .status(Status.TODO)
                .expirationDate(LocalDateTime.now())
                .userId(1L)
                .build();

        taskService.editTask(taskRequest);
        verify(taskRepository).save(any());
    }

    @Test
    void isTaskDone() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        task.setStatus(Status.DONE);

        when(taskRepository.findById(taskId))
                .thenReturn(Optional.of(task));

        boolean isTaskDone = taskService.isTaskDone(taskId);
        verify(taskRepository).findById(taskId);
        assertTrue(isTaskDone);
    }

    @Test
    void deleteTask() {
        taskService.deleteTask(1L);
        verify(taskRepository).deleteById(any());
    }

    @Test
    void getTasksByUserId() {
        Long userId = 1L;
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tasks.add(new Task());
        }

        when(taskRepository.findTasksByUserId(userId))
                .thenReturn(tasks);

        List<TaskResponse> testTasks = taskService.getTasksByUserId(userId);
        verify(taskRepository).findTasksByUserId(any());
        assertEquals(taskMapper.toTaskResponse(tasks), testTasks);
    }

    @Test
    void getAllSoonTasks() {
        Duration duration = Duration.ofHours(1);
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tasks.add(new Task());
        }

        when(taskRepository.findAllSoonTasks(any(), any()))
                .thenReturn(tasks);

        List<TaskResponse> testTasks = taskService.getAllSoonTasks(duration);
        verify(taskRepository).findAllSoonTasks(any(), any());
        assertEquals(taskMapper.toTaskResponse(tasks), testTasks);
    }
}