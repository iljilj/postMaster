package com.example.hop.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import com.example.hop.repository.UserRepository;
import com.example.hop.repository.RoleRepository;
import com.example.hop.model.User;
import com.example.hop.model.Role;
import com.example.hop.dto.UserDto;
import java.util.Optional;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        UserDto userDto = new UserDto();
        userDto.setName("testUser");
        userDto.setPassword("password");
        userDto.setAdmin(false);
        userDto.setBanned(false);

        BindingResult bindingResult = mock(BindingResult.class);

        when(userRepository.findByName(anyString())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(new Role()));

        userService.create(userDto, bindingResult);

        verify(userRepository, times(1)).save(any(User.class));
        verify(bindingResult, never()).rejectValue(anyString(), anyString(), anyString());
    }

    @Test
    public void testFindByName() {
        User user = new User();
        user.setId(1L);
        user.setName("testUser");
        when(userRepository.findByName("testUser")).thenReturn(user);

        UserDto userDto = userService.findByName("testUser");

        assertEquals("testUser", userDto.getName());
    }
}
