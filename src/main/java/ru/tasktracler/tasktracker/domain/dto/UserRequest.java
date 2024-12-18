package ru.tasktracler.tasktracker.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String name;

    private String email;

    private String password;

    private String passwordConfirmation;

}
