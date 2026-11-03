package com.devtiro.task.controller;

import com.devtiro.task.domain.dto.CreateTaskRequestDto;
import com.devtiro.task.domain.dto.TaskDto;
import com.devtiro.task.domain.dto.UpdateTaskRequestDto;
import com.devtiro.task.domain.entity.Task;
import com.devtiro.task.mapper.TaskMapper;
import com.devtiro.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(
            @Valid @RequestBody CreateTaskRequestDto dto) {
        Task created = taskService.createTask(taskMapper.fromDto(dto));
        return new ResponseEntity<>(taskMapper.toDto(created), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> listTasks() {
        List<TaskDto> tasks = taskService.listTasks()
                .stream()
                .map(taskMapper::toDto)
                .toList();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable("taskId") UUID taskId,
            @Valid @RequestBody UpdateTaskRequestDto dto) {
        Task updated = taskService.updateTask(taskId, taskMapper.fromDto(dto));
        return ResponseEntity.ok(taskMapper.toDto(updated));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") UUID taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}