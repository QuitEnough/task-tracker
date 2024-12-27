package ru.tasktracler.tasktracker.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response with user information")
public class UserResponse implements Serializable {

    private Long userId;

    private String name;

    private String email;

}
