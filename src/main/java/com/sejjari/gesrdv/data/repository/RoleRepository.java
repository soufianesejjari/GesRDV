package com.sejjari.gesrdv.data.repository;

import com.sejjari.gesrdv.data.entete.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {

    public Role findByName(String name) ;
}
