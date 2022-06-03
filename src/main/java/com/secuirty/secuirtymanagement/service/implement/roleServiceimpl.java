package com.secuirty.secuirtymanagement.service.implement;

import java.util.List;

import com.secuirty.secuirtymanagement.database.roleRepository;
import com.secuirty.secuirtymanagement.model.role;
import com.secuirty.secuirtymanagement.service.roleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class roleServiceimpl implements roleService {
    
    private Logger log = LoggerFactory.getLogger(userServiceimpl.class);

    @Autowired
    private roleRepository roleRepo;

    @Override
    public role addRole(role Role) {
        log.info("Adding user to database");

        return this.roleRepo.save(Role);
    }

    @Override
    public role getRole(long id) {
        log.info("Getting  user from  database with id ="+id);
        return this.roleRepo.findById(id).get();
    }

    @Override
    public void deleteRole(long id) {
        log.info("Deleting  user from  database with id ="+id);
        role Role = this.roleRepo.findById(id).get();
        this.roleRepo.delete(Role);
    }

    @Override
    public List<role> getAllRoles() {
        log.info("Getting all users from  database");

        return this.roleRepo.findAll();
    }


    
}
