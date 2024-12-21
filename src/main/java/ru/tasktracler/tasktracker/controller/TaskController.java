package ru.tasktracler.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tasktracler.tasktracker.domain.dto.TaskRequest;
import ru.tasktracler.tasktracker.domain.dto.TaskResponse;
import ru.tasktracler.tasktracker.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@RequestBody TaskRequest taskRequest) {
        log.debug("[RequestBody] Create Task with Details: {}", taskRequest);

        return taskService.createTask(taskRequest);
    }

    @GetMapping("/by-user")
    public List<TaskResponse> getTasksByUserId(@RequestParam("id") Long userId) {
        log.debug("[RequestParam] Get Task for User with id: {}", userId);
        return taskService.getTasksByUserId(userId);
    }

    @PutMapping("/update")
    public TaskResponse updateTask(@RequestBody TaskRequest taskRequest) {
        log.debug("[RequestBody] Update Task with Details: {}", taskRequest);
        return taskService.editTask(taskRequest);
    }

    @DeleteMapping("/delete")
    public void deleteTask(@RequestParam("id") Long taskId) {
        log.debug("[RequestParam] Delete Task with id: {}", taskId);
        taskService.deleteTask(taskId);
    }

}
