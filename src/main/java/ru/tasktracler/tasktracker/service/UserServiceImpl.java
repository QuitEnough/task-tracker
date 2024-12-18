package ru.tasktracler.tasktracker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tasktracler.tasktracker.domain.dto.UserRequest;
import ru.tasktracler.tasktracker.domain.dto.UserResponse;
import ru.tasktracler.tasktracker.domain.entity.User;
import ru.tasktracler.tasktracker.domain.mapper.UserMapper;
import ru.tasktracler.tasktracker.exception.ResourceNotFoundException;
import ru.tasktracler.tasktracker.repository.UserRepository;

import java.util.Optional;

import static ru.tasktracler.tasktracker.utils.CustomPasswordEncoder.encode;

@Service
@RequiredArgsConstructor
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
        User user = userMapper.toUser(userRequest);
        user.setPassword(encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(UserRequest userRequest, Long userId) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
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
        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with given id not found."));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with given email not found."));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getTaskAuthor(Long taskId) {
        User user = userRepository
                .findTaskAuthor(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
        return userMapper.toUserResponse(user);
    }

    @Override
    public Boolean isTaskOwner(Long userId, Long taskId) {
        return userRepository.isTaskOwner(userId, taskId);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
