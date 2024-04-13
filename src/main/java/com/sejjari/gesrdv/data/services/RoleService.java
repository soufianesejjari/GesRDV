package com.sejjari.gesrdv.data.services;

import com.sejjari.gesrdv.data.entete.Role;
import com.sejjari.gesrdv.data.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public Role getRoleByName(String name){
        return roleRepository.findByName(name);
    }
}
