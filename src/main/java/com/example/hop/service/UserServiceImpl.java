package com.example.hop.service;

import com.example.hop.dto.PasswordChange;
import com.example.hop.dto.UserDto;
import com.example.hop.model.Role;
import com.example.hop.model.User;
import com.example.hop.repository.RoleRepository;
import com.example.hop.repository.UserRepository;
import com.example.hop.util.MappingUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.hop.util.MappingUtils.mapUserToUserDto;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void create(UserDto userDto, BindingResult result) {
        if (isDisallowedUsername(userDto.getName())) {
            result.rejectValue("name", "error.user", "This username is not allowed.");
            return;
        }

        if (findByName(userDto.getName()) != null) {
            result.rejectValue("name", "error.user", "An account with this name already exists");
            return;
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        List<Role> roles = new ArrayList<>();

        if (userDto.isAdmin()) {
            Role adminRole = getOrCreateRole("ROLE_ADMIN");
            roles.add(adminRole);
        }

        Role userRole = getOrCreateRole("ROLE_USER");
        roles.add(userRole);

        user.setRoles(roles);
        user.setBanned(userDto.isBanned());

        userRepository.save(user);
    }

    private boolean isDisallowedUsername(String username) {
        List<String> disallowedUsernames = Arrays.asList("updatePassword", "delete", "posts");
        return disallowedUsernames.contains(username.toLowerCase());
    }

    private Role getOrCreateRole(String roleName) {
        return roleRepository.findByName(roleName).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName(roleName);
            return roleRepository.save(newRole);
        });
    }

    @Override
    public boolean updatePassword(Long userId, PasswordChange passwordChange){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(passwordChange.getOldPassword(), user.getPassword())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(passwordChange.getNewPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDto findByName(String name) {
        return mapUserToUserDto(userRepository.findByName(name));
    }

    @Override
    public Long getIdByName(String name) {
        return userRepository.findByName(name).getId();
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(MappingUtils::mapUserToUserDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAdmins() {
        List<User> admins = userRepository.findByRoles_Name("ROLE_ADMIN");
        return admins.stream().map(MappingUtils::mapUserToUserDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findBannedUsers() {
        List<User> bannedUsers = userRepository.findByIsBannedTrue();
        return bannedUsers.stream().map(MappingUtils::mapUserToUserDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        user.getRoles().clear();
        userRepository.save(user);
        userRepository.deleteById(id);
    }

    @Override
    public void banStatus(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        user.setBanned(!user.isBanned());
        userRepository.save(user);
    }

    @Override
    public void adminStatus(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        List<Role> roles = user.getRoles();
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Role not found"));
        if (roles.contains(adminRole)) {
            roles.remove(adminRole);
        } else {
            roles.add(adminRole);
        }
        userRepository.save(user);
    }
}