package ru.tasktracler.tasktracker.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "")
public class UserRequest {

    private String name;

    private String email;

    private String password;

    private String passwordConfirmation;

}
