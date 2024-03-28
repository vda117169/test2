package com.example.test.exception;

public class UserAccessException extends Throwable {
    public UserAccessException(String username, Integer taskId) {
        super("Пользователь: " + username + " не имеет доступ к задаче с taskId: " + taskId);
    }
}