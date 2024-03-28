package com.example.test.service;

import com.example.test.entity.Task;
import com.example.test.entity.User;
import com.example.test.exception.NotFoundTaskException;
import com.example.test.exception.TaskCreateException;
import com.example.test.exception.TaskUpdateException;
import com.example.test.exception.UserAccessException;
import com.example.test.repository.TaskRepository;
import lombok.SneakyThrows;
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

    @SneakyThrows
    public Task getTask(Integer taskId, String username) {
        // Проверка наличия задачи по taskId
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            throw new NotFoundTaskException(taskId);
        }

        // Проверка, что пользователь имеет доступ к задаче
        if (!task.getCreator().getUsername().equals(username)) {
            throw new UserAccessException(username, taskId);
        }
        return task;
    }

    @SneakyThrows
    public void createTask(Task task, String username) {
        try {
            User creator = new User();
            creator.setUsername(username);
            task.setCreator(creator);
            taskRepository.save(task);
        } catch (Exception e) {
            throw new TaskCreateException(task.getTitle());
        }
    }

    @SneakyThrows
    public void updateTask(Task task) {
        try {
            taskRepository.save(task);
        } catch (Exception e) {
            throw new TaskUpdateException(task.getTitle());
        }

    }

    @SneakyThrows
    public void deleteTask(Integer taskId, String username) {
        Task task = getTask(taskId, username);
        if (task != null) {
            taskRepository.delete(task);
        }
        throw new NotFoundTaskException(taskId);
    }

    @SneakyThrows
    public void addAssigneesToTask(Integer taskId, List<String> assignees) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null) {
            for (String assignee : assignees) {
                User assigneeUser = new User();
                assigneeUser.setUsername(assignee);
                task.getAssignees().add(assigneeUser);
            }
            taskRepository.save(task);
        }
        throw new NotFoundTaskException(taskId);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public boolean editTaskAfterCheck(Integer taskId, String username, Task taskDetails) {
        Task task = getTask(taskId, username);
        if (task != null && task.getCreator().getUsername().equals(username)) {
            updateTask(taskDetails);
            return true;
        }
        return false;
    }

    public boolean deleteTaskAfterCheck(Integer taskId, String username) {
        Task task = getTask(taskId, username);
        if (task != null && task.getCreator().getUsername().equals(username)) {
            deleteTask(taskId, username);
            return true;
        }
        return false;
    }

    public boolean addAssigneesToTaskAfterCheck(Integer taskId, String username, List<String> assignees) {
        Task task = getTask(taskId, username);
        if (task != null && task.getCreator().getUsername().equals(username)) {
            addAssigneesToTask(taskId, assignees);
            return true;
        }
        return false;
    }
}
