package ru.tasktracler.tasktracker.service;

import ru.tasktracler.tasktracker.domain.dto.UserRequest;
import ru.tasktracler.tasktracker.domain.dto.UserResponse;

public interface UserService {

    void createUser(UserRequest userRequest);

    void updateUser(UserRequest userRequest, Long userId);

    UserResponse getUserById(Long userId);

    UserResponse getUserByEmail(String email);

    UserResponse getTaskAuthor(Long taskId);

    Boolean isTaskOwner(Long userId, Long taskId);

    void deleteUser(Long userId);

}
