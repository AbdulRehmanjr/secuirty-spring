package com.secuirty.secuirtymanagement.controller;

import java.util.HashSet;
import java.util.Set;

import com.secuirty.secuirtymanagement.database.roleRepository;
import com.secuirty.secuirtymanagement.model.role;
import com.secuirty.secuirtymanagement.model.user;
import com.secuirty.secuirtymanagement.model.userRole;
import com.secuirty.secuirtymanagement.service.userService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/user")
public class userController {
    
    private Logger log = LoggerFactory.getLogger(userController.class);

    @Autowired
    private userService uService;
    @Autowired
    private roleRepository rRepo;

    private String Default_Role = "USER";

    @GetMapping("/show")
    public ResponseEntity<?> show(){
        return ResponseEntity.ok("SHow");
    }
    @GetMapping("/protected")
    public ResponseEntity<?> pro(){
        return ResponseEntity.ok("protected");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(uService.getAllUsers());
    }
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody user User) {
        
        role Role = this.rRepo.findByRoleName(Default_Role);
        
        if(Role!=null){
            userRole ur = new userRole();
            Set<userRole> roles = new HashSet<>();
            ur.setRole(Role);
            ur.setUser(User);    
            roles.add(ur);
            log.info("User created with Role : "+ur.getRole().getRoleName());

           user userResponse =    uService.addUser(User,roles);

           if(userResponse==null){
               log.error("User already created");
               return ResponseEntity.badRequest().body("User already exists");
           }
        }else{
            log.error("Role not found");
            return ResponseEntity.badRequest().body("Role not found");
        }

        
        log.info("Adding user to database {Controller class}");

        return ResponseEntity.ok("user created successfully");
    }
    
    @GetMapping("/{userId}")
    public user fetchUser(@PathVariable("userId") long userId) {
        log.info("Getting  user from {Controller class} database with id ="+userId);
        return this.uService.getUser(userId);
    }
}
