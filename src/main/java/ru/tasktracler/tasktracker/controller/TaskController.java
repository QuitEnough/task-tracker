package ru.tasktracler.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tasktracler.tasktracker.domain.dto.TaskRequest;
import ru.tasktracler.tasktracker.domain.dto.TaskResponse;
import ru.tasktracler.tasktracker.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<TaskResponse> getTaskById(@RequestParam("id") Long taskId) {
        TaskResponse response = taskService.getById(taskId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<TaskResponse> updateTask(@RequestBody TaskRequest taskRequest) {
        TaskResponse response = taskService.editTask(taskRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteTask(@RequestParam("id") Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
