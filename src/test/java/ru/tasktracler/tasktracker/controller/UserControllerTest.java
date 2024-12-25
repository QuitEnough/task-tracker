package ru.tasktracler.tasktracker.controller;

import org.junit.jupiter.api.Test;
import ru.tasktracler.tasktracker.domain.dto.UserRequest;
import ru.tasktracler.tasktracker.domain.dto.UserResponse;
import ru.tasktracler.tasktracker.service.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private final UserServiceImpl userService = mock(UserServiceImpl.class);

    private final UserController userController = new UserController(userService);

    @Test
    void createUser() {
        UserRequest userRequest = new UserRequest();

        userController.createUser(userRequest);
        verify(userService).createUser(userRequest);
    }

    @Test
    void updateUser() {
        Long userId = 1L;
        UserRequest userRequest = new UserRequest();

        userController.updateUser(userRequest, userId);
        verify(userService).updateUser(userRequest, userId);
    }

    @Test
    void getUser() {
        UserResponse userResponse = UserResponse
                .builder()
                .userId(1L)
                .name("name")
                .email("mail@mail.ru")
                .build();

        when(userService.getUserById(1L))
                .thenReturn(userResponse);
        when(userService.getUserByEmail(userResponse.getEmail()))
                .thenReturn(userResponse);

        var user = userController.getUser(1L, "mail@mail.ru");

        assertNotNull(user);
    }

    @Test
    void deleteUser() {
        Long userId = 1L;
        userController.deleteUser(userId);
        verify(userService).deleteUser(userId);
    }

}