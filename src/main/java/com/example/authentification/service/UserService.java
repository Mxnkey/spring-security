package com.example.authentification.service;

import com.example.authentification.entity.UserEntity;
import com.example.authentification.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserEntity findByUsername(String username){
        UserEntity user = null;
        try{
            user = userRepository.findByUsername(username);
        } catch (Exception e){
            throw e;
        }
        return user;
    }

    @Transactional
    public void changePassword(String oldPassword, String newPassword){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserEntity currentUser = findByUsername(currentPrincipalName);

        System.out.println(currentUser.getPassword());
        System.out.println(oldPassword);

        if(passwordEncoder.matches(oldPassword,currentUser.getPassword())){
            userRepository.changePassword(passwordEncoder.encode(newPassword),currentUser.getId());
        }else
            System.out.println("Password doesn't match");
    }

    @Transactional(readOnly = true)
    public List<String> findAllUsernames(){
        List<String> usernamesList= new ArrayList<>();

        try{
            usernamesList = userRepository.findAllUsername();
        } catch (Exception e){
            throw e;
        }

        return usernamesList;
    }

    @Transactional
    public void deleteUser(String username){

        UserEntity deletingUser = findByUsername(username);
        Long deletingUserId = deletingUser.getId();
        userRepository.deleteUser(deletingUserId);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
