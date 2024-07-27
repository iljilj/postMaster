package com.example.hop.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PostDtoTest {
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidPostDto() {
        PostDto postDto = new PostDto(1L, "Title", "Content", "username", LocalDateTime.now());
        Set<ConstraintViolation<PostDto>> violations = validator.validate(postDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testEmptyTitle() {
        PostDto postDto = new PostDto(1L, "", "Content", "username", LocalDateTime.now());
        Set<ConstraintViolation<PostDto>> violations = validator.validate(postDto);
        assertFalse(violations.isEmpty());
        assertThat(violations).extracting("message").contains("Post title should not be empty");
    }

    @Test
    public void testTooLongTitle() {
        String longTitle = "a".repeat(256);
        PostDto postDto = new PostDto(1L, longTitle, "Content", "username", LocalDateTime.now());
        Set<ConstraintViolation<PostDto>> violations = validator.validate(postDto);
        assertFalse(violations.isEmpty());
        assertThat(violations).extracting("message").contains("Post title must be less than or equal to 255 characters");
    }

    @Test
    public void testEmptyContent() {
        PostDto postDto = new PostDto(1L, "Title", "", "username", LocalDateTime.now());
        Set<ConstraintViolation<PostDto>> violations = validator.validate(postDto);
        assertFalse(violations.isEmpty());
        assertThat(violations).extracting("message").contains("Post content should not be empty");
    }

    @Test
    public void testTooLongContent() {
        String longContent = "a".repeat(5001);
        PostDto postDto = new PostDto(1L, "Title", longContent, "username", LocalDateTime.now());
        Set<ConstraintViolation<PostDto>> violations = validator.validate(postDto);
        assertFalse(violations.isEmpty());
        assertThat(violations).extracting("message").contains("Post content must be less than or equal to 5000 characters");
    }
}
