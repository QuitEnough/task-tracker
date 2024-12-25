package ru.tasktracler.tasktracker.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request for user information")
public class UserRequest {

    @Schema(description = "User name",
            example = "John Doe")
    @Size(min = 5, max = 255, message = "Name length must be bigger than 5 symbols and smaller than 255 symbols")
    private String name;

    @Schema(description = "Email address", example = "TsarVoDvortsa@BORAT.com")
    @Size(min = 5, max = 255, message = "Email must contains at least 5 characters and max to 255")
    @Email(message = "Email must be formatted as DimaBilan@gmail.com",
            regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Schema(description = "User encrypted password", example = "TsarVoDvortsa1sBORAT")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(message = "Password must be from 6 to 30 characters and contains at least one uppercase letter",
            regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,30}$")
    private String password;

    @Schema(description = "User password confirmation")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password confirmation can not be null")
    private String passwordConfirmation;

}
