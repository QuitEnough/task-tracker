package ru.tasktracler.tasktracker.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.tasktracler.tasktracker.domain.dto.UserRequest;
import ru.tasktracler.tasktracker.domain.dto.UserResponse;
import ru.tasktracler.tasktracker.domain.entity.MailType;
import ru.tasktracler.tasktracker.domain.entity.User;
import ru.tasktracler.tasktracker.domain.mapper.UserMapper;
import ru.tasktracler.tasktracker.exception.ResourceNotFoundException;
import ru.tasktracler.tasktracker.repository.UserRepository;
import ru.tasktracler.tasktracker.service.MailService;
import ru.tasktracler.tasktracker.service.UserService;

import java.util.Properties;

import static ru.tasktracler.tasktracker.utils.CustomPasswordEncoder.encode;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final MailService mailService;

    @Override
    @Transactional
    @Caching(cacheable = {
            @Cacheable(value = "UserService::getUserByEmail",
                    condition = "#userRequest.email!=null",
                    key = "#userRequest.email")
    })
    public void createUser(UserRequest userRequest) {
        if (!userRequest.getPassword().equals(userRequest.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation do not match.");
        }
        log.debug("[UserService] Saving user with Details {}", userRequest);

        User user = userMapper.toUser(userRequest);
        user.setPassword(encode(user.getPassword()));
        userRepository.save(user);
        mailService.sendEmail(user, MailType.REGISTRATION, new Properties());
    }

    @Override
    @Transactional
    @Caching(put = {
            @CachePut(value = "UserService::getUserById", key = "#userId"),
            @CachePut(value = "UserService::getUserByEmail", key = "#userRequest.email")
    })
    public void updateUser(UserRequest userRequest, Long userId) {
        log.debug("[UserService] Updating user's details to: {}", userRequest);

        userRepository.updateUserById(
                userRequest.getName(),
                userRequest.getEmail(),
                encode(userRequest.getPassword()),
                userId);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getUserById", key = "#userId")
    public UserResponse getUserById(Long userId) {
        log.debug("[UserService] Getting user Details with id: {}", userId);

        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with given id not found."));
        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getUserByEmail", key = "#email")
    public UserResponse getUserByEmail(@NotNull String email) {
        log.debug("[UserService] Getting user Details with email: {}", email);

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with given email not found."));
        return userMapper.toUserResponse(user);
    }

    @Override
    @Cacheable(value = "UserService::getTaskAuthor", key = "#taskId")
    public UserResponse getTaskAuthor(Long taskId) {
        log.debug("[UserService] Getting user Details for task with id: {}", taskId);

        User user = userRepository
                .findTaskAuthor(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
        return userMapper.toUserResponse(user);
    }

    @Override
    @Cacheable(value = "UserService::isTaskOwner", key = "#userId + '.' + #taskId")
    public Boolean isTaskOwner(Long userId, Long taskId) {
        log.debug("[UserService] Checking if user with id {} is owner of task with id: {}", userId, taskId);
        return userRepository.isTaskOwner(userId, taskId);
    }

    @Override
    @CacheEvict(value = "UserService::getUserById", key = "#userId")
    public void deleteUser(Long userId) {
        log.debug("[UserService] Deleting user with id: {}", userId);
        userRepository.deleteById(userId);
    }

}
