package com.example.authentification.service;

import com.example.authentification.entity.RoleEntity;
import com.example.authentification.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;
    public RoleEntity findByRoleName(String roleName){
        RoleEntity roleEntity = null;

        try{
            roleEntity = roleRepository.findByRoleName(roleName);
        } catch (Exception e){
            throw e;
        }
        return roleEntity;
    }

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }
}
