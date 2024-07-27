package com.example.hop.controller;

import com.example.hop.dto.UserDto;
import com.example.hop.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void userHome_ShouldRedirectToUserPage() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/testuser"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void viewUser_ShouldReturnUserPage() throws Exception {
        UserDto userDto = new UserDto();
        when(userService.findByName("testuser")).thenReturn(userDto);

        mockMvc.perform(get("/user/testuser"))
                .andExpect(status().isOk())
                .andExpect(view().name("user"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void deleteUser_ShouldReturnDeleteAccountPage() throws Exception {
        UserDto userDto = new UserDto();
        when(userService.findByName("testuser")).thenReturn(userDto);

        mockMvc.perform(get("/user/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("delete_account"))
                .andExpect(model().attributeExists("user"));
    }


    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void updatePasswordForm_ShouldReturnUpdatePasswordPage() throws Exception {
        mockMvc.perform(get("/user/updatePassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("update_password"))
                .andExpect(model().attributeExists("passwordChange"));
    }

}
