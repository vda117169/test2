package com.example.test.controller;

import com.example.test.entity.Task;
import com.example.test.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Task> getTask(@PathVariable Long taskId, @RequestParam String username) {
        Task task = taskService.getTask(taskId, username);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task, @RequestParam String username) {
        // Логика создания задачи
        taskService.createTask(task, username);
        return ResponseEntity.ok("Task created successfully");
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId, @RequestBody Task taskDetails, @RequestParam String username) {
        // Проверка на то, что пользователь, пытающийся изменить задачу, является её создателем
        Task task = taskService.getTask(taskId, username);
        if (task != null && task.getCreator().equals(username)) {
            taskService.updateTask(taskId, username);
            return ResponseEntity.ok("Task updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to update this task");
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId, @RequestParam String username) {
        // Проверка на то, что пользователь, пытающийся удалить задачу, является её создателем
        Task task = taskService.getTask(taskId, username);
        if (task != null && task.getCreator().equals(username)) {
            taskService.deleteTask(taskId, username);
            return ResponseEntity.ok("Task deleted");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to delete this task");
        }
    }

    @PutMapping("/{taskId}/assign")
    public ResponseEntity<?> addAssigneesToTask(@PathVariable Long taskId, @RequestParam List<String> assignees, @RequestParam String username) {
        // Проверка на то, что пользователь, пытающийся добавить ответственных, является создателем задачи
        Task task = taskService.getTask(taskId, username);
        if (task != null && task.getCreator().equals(username)) {
            taskService.addAssigneesToTask(taskId, assignees);
            return ResponseEntity.ok("Assignees added");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized add assignees to this task");
        }
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }


}
