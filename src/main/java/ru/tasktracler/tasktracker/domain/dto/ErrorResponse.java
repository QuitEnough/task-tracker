package ru.tasktracler.tasktracker.domain.dto;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final Integer statusCode;
    private final String message;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
