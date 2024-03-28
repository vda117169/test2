package com.example.test.exception;

public class TaskCreateException extends Throwable {
    public TaskCreateException(String title) {
        super("Ошибка при создании задачи с title: " + title);
    }
}