package com.example.hop.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class UsernameChangeTest {

    private final Validator validator;

    public UsernameChangeTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUsernameNotEmpty() {
        UsernameChange usernameChange = new UsernameChange("");
        Set<ConstraintViolation<UsernameChange>> violations = validator.validate(usernameChange);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Username should not be empty")));
    }

    @Test
    public void testUsernamePattern() {
        UsernameChange usernameChange = new UsernameChange("user123");
        Set<ConstraintViolation<UsernameChange>> violations = validator.validate(usernameChange);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Username must contain only letters")));
    }

    @Test
    public void testUsernameSize() {
        UsernameChange usernameChange = new UsernameChange("a".repeat(256));
        Set<ConstraintViolation<UsernameChange>> violations = validator.validate(usernameChange);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Username must be less than or equal to 255 characters")));
    }
}
