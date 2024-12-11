package ru.tasktracler.tasktracker.domain.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

    private Long id;

    private String title;

    private String description;

    private Status status;

    private LocalDateTime expirationDate;

    private Long userId;

}
