package ru.tasktracler.tasktracker.utils;

import org.springframework.util.DigestUtils;

import java.util.Objects;

public class CustomPasswordEncoder {

    public static String encode(CharSequence rawPassword) {
        return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String rawEncoded = encode(rawPassword);
        return Objects.equals(rawEncoded, encodedPassword);
    }

}
