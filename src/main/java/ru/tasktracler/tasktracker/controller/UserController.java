package ru.tasktracler.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tasktracler.tasktracker.domain.dto.UserRequest;
import ru.tasktracler.tasktracker.domain.dto.UserResponse;
import ru.tasktracler.tasktracker.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody UserRequest request) {
        log.debug("[RequestBody] create User with Details {}: ", request);

        userService.createUser(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestBody UserRequest request,
                                           @RequestParam("id") Long userId) {
        log.debug("[RequestParams] update User with id {} to new Details: {}", userId, request);

        userService.updateUser(request, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserResponse> getUser(@RequestParam(required = false) Long id,
                                                @RequestParam(required = false) String email) {
        log.debug("[RequestParams] get User with id {} or email {}", id, email);

        UserResponse userResponse;
        if (id != null) {
            userResponse = userService.getUserById(id);
        } else {
            userResponse = userService.getUserByEmail(email);
        }
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestParam("id") Long userId) {
        log.debug("[RequestParams] delete User with id {}", userId);

        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
