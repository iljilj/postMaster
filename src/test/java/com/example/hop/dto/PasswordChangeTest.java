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

public class PasswordChangeTest {
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidPasswordChange() {
        PasswordChange passwordChange = new PasswordChange("oldPassword123", "newPassword123");
        Set<ConstraintViolation<PasswordChange>> violations = validator.validate(passwordChange);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testEmptyOldPassword() {
        PasswordChange passwordChange = new PasswordChange("", "newPassword123");
        Set<ConstraintViolation<PasswordChange>> violations = validator.validate(passwordChange);
        assertFalse(violations.isEmpty());
        assertThat(violations).extracting("message").contains("Old password should not be empty");
    }

    @Test
    public void testShortNewPassword() {
        PasswordChange passwordChange = new PasswordChange("oldPassword123", "short");
        Set<ConstraintViolation<PasswordChange>> violations = validator.validate(passwordChange);
        assertFalse(violations.isEmpty());
        assertThat(violations).extracting("message").contains("Password must be between 8 and 255 characters");
    }
}
