package com.secuirty.secuirtymanagement.controller;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secuirty.secuirtymanagement.database.roleRepository;
import com.secuirty.secuirtymanagement.model.role;
import com.secuirty.secuirtymanagement.model.user;
import com.secuirty.secuirtymanagement.model.userRole;
import com.secuirty.secuirtymanagement.service.userService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:4200")
public class userController {

    private Logger log = LoggerFactory.getLogger(userController.class);

    @Autowired
    private userService uService;
    @Autowired
    private roleRepository rRepo;

    private String Default_Role = "USER";

    @GetMapping("/show")
    public ResponseEntity<?> show() {
        return ResponseEntity.ok("SHow");
    }

    @GetMapping("/protected")
    public ResponseEntity<?> pro() {
        return ResponseEntity.ok("protected");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(uService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestParam("file") MultipartFile picture,
            @RequestParam("user") String userString) {

        role Role = this.rRepo.findByRoleName(Default_Role);
        user User;

        if (Role != null) {
            userRole ur = new userRole();
            Set<userRole> roles = new HashSet<>();
            try {
                User = new ObjectMapper().readValue(userString, user.class);
                ur.setRole(Role);
                ur.setUser(User);
                roles.add(ur);
                log.info("User created with Role : " + ur.getRole().getRoleName());
                user userResponse = uService.addUser(picture, User, roles);
                if (userResponse == null) {
                    log.error("User already created");
                    return ResponseEntity.badRequest().body("User already exists");
                }
            } catch (JsonProcessingException e) {
                log.error("error in object mapping");
                e.printStackTrace();
            }

        } else {
            log.error("Role not found");
            return ResponseEntity.badRequest().body("Role not found");
        }

        log.info("Adding user to database {Controller class}");

        return ResponseEntity.ok("user created successfully");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> get(@PathVariable("userId") long userId) {
        String fileName = "user";
        user User = this.uService.getUser(userId);
        byte[] picture = User.getUserDp();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("User", User.toString());
        responseHeaders.add("Content-Disposition", "attachment; filename=" + fileName);
        // responseHeaders.add("Access-Control-Expose-Headers", "Authorization");
        // responseHeaders.add("Access-Control-Allow-Headers", "Authorization,
        // X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept,
        // X-Custom-header");
        //responseHeaders.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
       // responseHeaders.add("Access-Control-Allow-Headers", "*");
       // responseHeaders.add("Access-Control-Allow-Credentials", "true");
       // responseHeaders.add("Access-Control-Allow-Origin", "http://localhost:4200");
        responseHeaders.add("Access-Control-Expose-Headers", "*");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(User.getDpType()))
                .headers(responseHeaders)
                .body(picture);
    }
}
