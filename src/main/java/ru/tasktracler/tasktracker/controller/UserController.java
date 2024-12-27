package ru.tasktracler.tasktracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tasktracler.tasktracker.domain.dto.UserRequest;
import ru.tasktracler.tasktracker.domain.dto.UserResponse;
import ru.tasktracler.tasktracker.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User Controller", description = "User API")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Sign up the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "User invalid values"),
            @ApiResponse(responseCode = "500", description = "User null")
    })
    public void createUser(@RequestBody @Valid UserRequest request) {
        log.debug("[RequestBody] create User with Details {}: ", request);
        userService.createUser(request);
    }

    @PutMapping("/update")
    @Operation(summary = "Update the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "400", description = "Request null"),
            @ApiResponse(responseCode = "500", description = "User null or invalid values")
    })
    public void updateUser(@RequestBody @Valid UserRequest request,
                           @RequestParam("id") Long userId) {
        log.debug("[RequestParams] update User with id {} to new Details: {}", userId, request);
        userService.updateUser(request, userId);
    }

    @GetMapping
    @Operation(summary = "Get User by Id or Email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> getUser(@RequestParam(required = false) Long id,
                                                @RequestParam(required = false) String email) {
        log.debug("[RequestParams] get User with id {} or email {}", id, email);
        if (id == null && email == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (id != null) {
            return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete User by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public void deleteUser(@RequestParam("id") Long userId) {
        log.debug("[RequestParams] delete User with id {}", userId);
        userService.deleteUser(userId);
    }

}
