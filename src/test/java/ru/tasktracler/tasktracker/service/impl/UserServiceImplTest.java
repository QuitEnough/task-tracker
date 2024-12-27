package ru.tasktracler.tasktracker.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.tasktracler.tasktracker.domain.dto.UserRequest;
import ru.tasktracler.tasktracker.domain.dto.UserResponse;
import ru.tasktracler.tasktracker.domain.entity.MailType;
import ru.tasktracler.tasktracker.domain.entity.User;
import ru.tasktracler.tasktracker.domain.mapper.UserMapper;
import ru.tasktracler.tasktracker.exception.ResourceNotFoundException;
import ru.tasktracler.tasktracker.repository.UserRepository;
import ru.tasktracler.tasktracker.service.impl.MailServiceImpl;
import ru.tasktracler.tasktracker.service.impl.UserServiceImpl;
import ru.tasktracler.tasktracker.utils.CustomPasswordEncoder;

import java.util.Optional;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private final UserRepository userRepository = mock(UserRepository.class);

    private final UserMapper userMapper = mock(UserMapper.class);

    private final MailServiceImpl mailService = mock(MailServiceImpl.class);

    private final UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, mailService);

    @Test
    void createUser() {
        String email = "username@gmail.com";
        String password = "password";
        UserRequest userRequest = UserRequest
                .builder()
                .name("name")
                .email("username@gmail.com")
                .password("password")
                .passwordConfirmation("password")
                .build();

        User user = new User();
        user.setPassword(password);
        when(CustomPasswordEncoder.encode(password))
                .thenReturn("encodedPassword");
        userService.createUser(userRequest);
        verify(userRepository).save(userMapper.toUser(userRequest));
        verify(mailService)
                .sendEmail(user, MailType.REGISTRATION, new Properties());
    }

    @Test
    void updateUser() {
        Long userId = 1L;
        String password = "password";
        User user = new User();
        user.setId(userId);
        user.setPassword(password);

        when(CustomPasswordEncoder.encode(password))
                .thenReturn("encodedPassword");
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        userService.updateUser(userMapper.toUserRequest(user), userId);
        verify(userRepository).updateUserById(any(), any(), any(), any());
    }

    @Test
    void getUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        UserResponse testUser = userService.getUserById(userId);
        verify(userRepository).findById(userId);
        assertEquals(userMapper.toUserResponse(user), testUser);
    }

    @Test
    void getByNotExistingId() {
        Long userId = 1L;

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getUserById(userId));
        verify(userRepository).findById(userId);
    }

    @Test
    void getUserByEmail() {
        String email = "email@gmail.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        UserResponse testUser = userService.getUserByEmail(email);
        verify(userRepository).findByEmail(email);
        assertEquals(userMapper.toUserResponse(user), testUser);
    }

    @Test
    void getUserByNotExistingEmail() {
        String email = "email@gmail.com";

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getUserByEmail(email));
        verify(userRepository).findByEmail(email);
    }

    @Test
    void getTaskAuthor() {
        Long taskId = 1L;
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findTaskAuthor(taskId))
                .thenReturn(Optional.of(user));

        UserResponse author = userService.getTaskAuthor(taskId);
        verify(userRepository).findTaskAuthor(taskId);
        assertEquals(userMapper.toUserResponse(user), author);
    }

    @Test
    void getNotExistingTaskAuthor() {
        Long taskId = 1L;

        when(userRepository.findTaskAuthor(taskId))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                userService.getTaskAuthor(taskId));
        verify(userRepository).findTaskAuthor(taskId);
    }

    @Test
    void isTaskOwner() {
        Long userId = 1L;
        Long taskId = 1L;

        when(userRepository.isTaskOwner(userId, taskId))
                .thenReturn(true);

        boolean isOwner = userService.isTaskOwner(userId, taskId);
        verify(userRepository).isTaskOwner(userId, taskId);
        assertTrue(isOwner);
    }

    @Test
    void deleteUser() {
        Long userId = 1L;
        userService.deleteUser(userId);
        verify(userRepository).deleteById(userId);
    }

}