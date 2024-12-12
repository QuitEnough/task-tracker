package ru.tasktracler.tasktracker.domain.dto;

import lombok.*;
import ru.tasktracler.tasktracker.domain.entity.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {

    private Long taskId;

    private String title;

    private String description;

    private Status status;

    private LocalDateTime expirationDate;

    private Long userId;

}
