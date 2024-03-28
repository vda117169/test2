package com.example.test.exception;

public class NotFoundTaskException extends Exception {
    public NotFoundTaskException(Integer taskId) {
        super("Задача по taskId: " + taskId + " не найдена");
    }
}