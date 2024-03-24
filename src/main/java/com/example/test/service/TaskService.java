package com.example.test.service;

import com.example.test.entity.Task;
import com.example.test.entity.User;
import com.example.test.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTask(Long taskId, String username) {
        // Проверка наличия задачи по taskId
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return null;
        }

        // Проверка, что пользователь имеет доступ к задаче
        if (!task.getCreator().getUsername().equals(username)) {
            return null;
        }

        return task;
    }

    public void createTask(Task task, String username) {
        User creator = new User();
        creator.setUsername(username);
        task.setCreator(creator);
        taskRepository.save(task);
    }

    public void updateTask(Long taskId, String username) {
        Task task = getTask(taskId, username);
        if (task != null) {
            taskRepository.save(task);
        }
    }

    public void deleteTask(Long taskId, String username) {
        Task task = getTask(taskId, username);
        if (task != null) {
            taskRepository.delete(task);
        }
    }

    public void addAssigneesToTask(Long taskId, List<String> assignees) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null) {
            for (String assignee: assignees) {
                User assigneeUser = new User();
                assigneeUser.setUsername(assignee);
                task.getAssignees().add(assigneeUser);
            }
            taskRepository.save(task);
        }
    }
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
