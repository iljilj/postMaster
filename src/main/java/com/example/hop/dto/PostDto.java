package com.example.hop.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDto {
    private Long id;

    @NotEmpty(message = "Post title should not be empty")
    @Size(max = 255, message = "Post title must be less than or equal to 255 characters")
    private String title;

    @NotEmpty(message = "Post content should not be empty")
    @Size(max = 5000, message = "Post content must be less than or equal to 5000 characters")
    private String content;

    private String username;
    private LocalDateTime creationTime;

    public Date getCreationTimeAsDate() {
        return Date.from(creationTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
