package com.example.test.dto;

import com.example.test.entity.Task;
import lombok.Data;

@Data
public class TaskResponse {
    private Task task;
    private boolean checkResult;
}
