package com.example.test.exception;

public class TaskUpdateException extends Throwable {
    public TaskUpdateException(String title) {
        super("Ошибка при обновлении задачи с title: " + title);
    }
}