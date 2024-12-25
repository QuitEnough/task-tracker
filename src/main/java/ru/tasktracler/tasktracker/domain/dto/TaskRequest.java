package ru.tasktracler.tasktracker.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.tasktracler.tasktracker.domain.entity.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request for task details")
public class TaskRequest {

    private Long taskId;

    @NotNull(message = "Title must be not null")
    @Size(min = 5, max = 255, message = "Title length must be bigger than 5 symbols and smaller than 255 symbols")
    private String title;

    @Size(min = 5, max = 255, message = "Description length must be bigger than 5 symbols and smaller than 255 symbols")
    private String description;

    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expirationDate;

    private Long userId;

}
