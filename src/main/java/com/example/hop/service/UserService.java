package com.example.hop.service;

import com.example.hop.dto.PasswordChange;
import com.example.hop.dto.UserDto;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UserService {
    void create(UserDto userDto, BindingResult result);

    UserDto findByName(String name);

    Long getIdByName(String name);

    List<UserDto> findAll();

    List<UserDto> findAdmins();

    List<UserDto> findBannedUsers();

    void delete(Long id);

    boolean updatePassword(Long id, PasswordChange passwordChange);

    void banStatus(Long id);

    void adminStatus(Long id);

}