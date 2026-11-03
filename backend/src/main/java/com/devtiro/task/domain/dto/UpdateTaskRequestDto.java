package com.devtiro.task.domain.dto;

import com.devtiro.task.domain.entity.TaskPriority;
import com.devtiro.task.domain.entity.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record UpdateTaskRequestDto(
        @NotBlank(message = "Title must not be blank")
        @Length(max = 255, message = "Title must be less than 255 characters")
        String title,

        @Length(max = 1000, message = "Description must be less than 1000 characters")
        String description,

        @FutureOrPresent(message = "Due date must be today or in the future")
        LocalDate dueDate,

        @NotNull(message = "Task status must be provided")
        TaskStatus status,

        @NotNull(message = "Task priority must be provided")
        TaskPriority priority
) {
}