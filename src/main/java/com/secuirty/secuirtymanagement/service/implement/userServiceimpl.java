package com.secuirty.secuirtymanagement.service.implement;

import java.util.List;
import java.util.Set;

import com.secuirty.secuirtymanagement.database.userRepository;
import com.secuirty.secuirtymanagement.model.user;
import com.secuirty.secuirtymanagement.model.userRole;
import com.secuirty.secuirtymanagement.service.userService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userServiceimpl implements userService{

    private Logger log = LoggerFactory.getLogger(userServiceimpl.class);

    @Autowired
    private userRepository userRepo;
    @Autowired
    private PasswordEncoder encoder;
    
    @Override
    public user addUser(user User, Set<userRole> roles) {

        user searchUser = this.userRepo.findByUserName(User.getUserName());

        if(searchUser!=null){
            log.error("User already exists");
            return null;
        }
        else{
            User.getUserRoles().addAll(roles);
            User.setUserPassword(encoder.encode(User.getUserPassword()));
            searchUser = this.userRepo.save(User);
            log.info("User created with Role : "+searchUser.getUserRoles().iterator().next().getRole().getRoleName());
        }

        return searchUser;
    }


    @Override
    public user getUser(long id) {
        log.info("Getting  user from  database with id ="+id);
        return this.userRepo.findById(id).get();
    }

    @Override
    public void deleteUser(long id) {
        log.info("Deleting  user from  database with id ="+id);
        user User = this.userRepo.findById(id).get();
        this.userRepo.delete(User);
        
    }

    @Override
    public List<user> getAllUsers() {

        log.info("Getting all users from  database");

        return this.userRepo.findAll();
    }

    
}
