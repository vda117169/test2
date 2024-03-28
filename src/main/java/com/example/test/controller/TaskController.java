package com.example.test.controller;

import com.example.test.dto.TaskResponse;
import com.example.test.entity.Task;
import com.example.test.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Task> getTask(@PathVariable Integer taskId, @RequestParam String username) {
        Task task = taskService.getTask(taskId, username);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createTask(@RequestBody Task task, @RequestParam String username) {
        taskService.createTask(task, username);
        return ResponseEntity.ok("Task created successfully");
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateTask(@PathVariable Integer taskId, @RequestBody Task taskDetails, @RequestParam String username) {
        boolean result = taskService.editTaskAfterCheck(taskId, username, taskDetails);
        if (result) {
            return ResponseEntity.ok("Task updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to update this task");
        }
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteTask(@PathVariable Integer taskId, @RequestParam String username) {
        boolean result = taskService.deleteTaskAfterCheck(taskId, username);
        if(result) {
            return ResponseEntity.ok("Task deleted");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to delete this task");
        }
    }

    @PutMapping("/{taskId}/assign")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addAssigneesToTask(@PathVariable Integer taskId, @RequestParam List<String> assignees, @RequestParam String username) {
        boolean result = taskService.addAssigneesToTaskAfterCheck(taskId, username, assignees);
        if(result){
            return ResponseEntity.ok("Assignees added");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized add assignees to this task");
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }


}
