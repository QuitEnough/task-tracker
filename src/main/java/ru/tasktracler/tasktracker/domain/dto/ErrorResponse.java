package ru.tasktracler.tasktracker.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final Integer statusCode;
    private final String message;

}
