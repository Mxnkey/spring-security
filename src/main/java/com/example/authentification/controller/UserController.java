package com.example.authentification.controller;

import com.example.authentification.entity.RoleEntity;
import com.example.authentification.entity.UserEntity;
import com.example.authentification.security.PasswordUpdate;
import com.example.authentification.service.CustomUserDetailsService;
import com.example.authentification.service.RoleService;
import com.example.authentification.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import  java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    private final RoleService roleService;

    public UserController(CustomUserDetailsService userDetailsService, UserService userService, RoleService roleService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping
    public List<UserDetails> getAdmin(){
        return Collections.singletonList(userDetailsService.loadUserByUsername("admin"));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("all")
    public List<List<UserDetails>> getAllUsers(){

        List<List<UserDetails>> allUsersList = new ArrayList<>();
        List<String> usernamesList = userService.findAllUsernames();

        usernamesList.forEach( username -> allUsersList.add(Collections.singletonList(userDetailsService.loadUserByUsername(username))));

        return allUsersList;
    }

    @GetMapping("me")
    public List<UserDetails> getCurrentUser(){

        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof UserDetails) {
            String username = ((UserDetails) user).getUsername();
            return Collections.singletonList(userDetailsService.loadUserByUsername(username));
        } else {
            String username = user.toString();
            return Collections.singletonList(userDetailsService.loadUserByUsername(username));
        }


    }

    @GetMapping("{username}")
    public List<UserDetails> getAllByUsername(@PathVariable String username){
        return Collections.singletonList(userDetailsService.loadUserByUsername(username));
    }

    @PostMapping
    public void changePassword(@RequestBody PasswordUpdate passwordUpdate){
        userService.changePassword(passwordUpdate.getOldPassword(),passwordUpdate.getNewPassword());
    }

    @PostMapping("create")
    public void createUser(@RequestBody UserEntity user){
        RoleEntity role = roleService.findByRoleName("ROLE_USER");
        user.setPassword(userService.getPasswordEncoder().encode(user.getPassword()));

        if(user.getRole() == null){
            user.setRole(role);
        }

        userService.getUserRepository().save(user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("{username}")
    public void deleteUser(@PathVariable String username){
        userService.deleteUser(username);
    }


}
