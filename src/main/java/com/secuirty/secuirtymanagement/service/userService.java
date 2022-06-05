package com.secuirty.secuirtymanagement.service;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.secuirty.secuirtymanagement.model.user;
import com.secuirty.secuirtymanagement.model.userRole;

public interface userService {
    
    public user addUser(MultipartFile picture,user User,Set<userRole> roles);

    public user getUser(long id);
    
    public void deleteUser(long id);


    public List<user> getAllUsers();
}
