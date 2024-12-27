package ru.tasktracler.tasktracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tasktracler.tasktracker.domain.dto.TaskRequest;
import ru.tasktracler.tasktracker.domain.dto.TaskResponse;
import ru.tasktracler.tasktracker.service.TaskService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Tag(name = "Task Controller", description = "Task API")
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create the Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created"),
            @ApiResponse(responseCode = "400", description = "Task info null or invalid")
    })
    public TaskResponse createTask(@RequestBody @Valid TaskRequest taskRequest) {
        log.debug("[RequestBody] Create Task with Details: {}", taskRequest);
        return taskService.createTask(taskRequest);
    }

    @GetMapping("/by-user")
    @Operation(summary = "Get Tasks by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks returned"),
            @ApiResponse(responseCode = "400", description = "Request null or invalid")
    })
    public List<TaskResponse> getTasksByUserId(@RequestParam("id") Long userId) {
        log.debug("[RequestParam] Get Task for User with id: {}", userId);
        return taskService.getTasksByUserId(userId);
    }

    @PutMapping("/update")
    @Operation(summary = "Update the Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated"),
            @ApiResponse(responseCode = "400", description = "Task info null or invalid"),
            @ApiResponse(responseCode = "404", description = "Task was not found")
    })
    public TaskResponse updateTask(@RequestBody @Valid TaskRequest taskRequest) {
        log.debug("[RequestBody] Update Task with Details: {}", taskRequest);
        return taskService.editTask(taskRequest);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete the Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task deleted"),
            @ApiResponse(responseCode = "400", description = "Request null or invalid")
    })
    public void deleteTask(@RequestParam("id") Long taskId) {
        log.debug("[RequestParam] Delete Task with id: {}", taskId);
        taskService.deleteTask(taskId);
    }

}
