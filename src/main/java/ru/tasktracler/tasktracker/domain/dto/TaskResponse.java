package ru.tasktracler.tasktracker.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.tasktracler.tasktracker.domain.entity.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response with task information")
public class TaskResponse {

    private Long taskId;

    private String title;

    private String description;

    private Status status;

    private LocalDateTime expirationDate;

}
