package ru.tasktracler.tasktracker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.tasktracler.tasktracker.domain.dto.UserRequest;
import ru.tasktracler.tasktracker.domain.dto.UserResponse;
import ru.tasktracler.tasktracker.domain.entity.User;
import ru.tasktracler.tasktracker.domain.mapper.UserMapper;
import ru.tasktracler.tasktracker.exception.ResourceNotFoundException;
import ru.tasktracler.tasktracker.repository.UserRepository;

import static ru.tasktracler.tasktracker.utils.CustomPasswordEncoder.encode;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public void createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new IllegalStateException("User already exists.");
        }
        if (!userRequest.getPassword().equals(userRequest.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation do not match.");
        }
        log.debug("[UserService] Saving user with Details {}", userRequest);

        User user = userMapper.toUser(userRequest);
        user.setPassword(encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(UserRequest userRequest, Long userId) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            log.debug("[UserService] Updating user's details to: {}", userRequest);

            userRepository.updateUserById(
                    userRequest.getName(),
                    userRequest.getEmail(),
                    encode(userRequest.getPassword()),
                    userId);
        } else {
            throw new ResourceNotFoundException("User does not exists.");
        }
    }

    @Override
    public UserResponse getUserById(Long userId) {
        log.debug("[UserService] Getting user Details with id: {}", userId);

        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with given id not found."));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(@NotNull String email) {
        log.debug("[UserService] Getting user Details with email: {}", email);

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with given email not found."));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getTaskAuthor(Long taskId) {
        log.debug("[UserService] Getting user Details for task with id: {}", taskId);

        User user = userRepository
                .findTaskAuthor(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
        return userMapper.toUserResponse(user);
    }

    @Override
    public Boolean isTaskOwner(Long userId, Long taskId) {
        log.debug("[UserService] Checking if user with id {} is owner of task with id: {}", userId, taskId);
        return userRepository.isTaskOwner(userId, taskId);
    }

    @Override
    public void deleteUser(Long userId) {
        log.debug("[UserService] Deleting user with id: {}", userId);
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new ResourceNotFoundException("User with given id not found");
        }
    }

}
