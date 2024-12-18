package ru.tasktracler.tasktracker.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long userId;

    private String name;

    private String email;

}
