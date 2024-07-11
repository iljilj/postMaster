package com.example.hop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Username must contain only letters")
    @Size(max = 40, message = "Username must be less than or equal to 255 characters")
    private String name;

    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;

    private boolean isAdmin;
    private boolean isBanned;

    private List<PostDto> posts;
}
