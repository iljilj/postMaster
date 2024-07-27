package com.example.hop.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserDtoTest {
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidUserDto() {
        UserDto userDto = new UserDto(1L, "username", "password123", true, false, null);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testEmptyName() {
        UserDto userDto = new UserDto(1L, "", "password123", true, false, null);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
        assertThat(violations).extracting("message").contains("Name should not be empty");
    }

    @Test
    public void testInvalidCharactersInName() {
        UserDto userDto = new UserDto(1L, "Invalid123", "password123", true, false, null);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
        assertThat(violations).extracting("message").contains("Username must contain only letters");
    }

    @Test
    public void testTooLongName() {
        String longName = "a".repeat(256);
        UserDto userDto = new UserDto(1L, longName, "password123", true, false, null);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
        assertThat(violations).extracting("message").contains("Username must be less than or equal to 255 characters");
    }

    @Test
    public void testShortPassword() {
        UserDto userDto = new UserDto(1L, "username", "short", true, false, null);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
        assertThat(violations).extracting("message").contains("Password must be between 8 and 255 characters");
    }
}
