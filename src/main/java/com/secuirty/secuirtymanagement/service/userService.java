package com.secuirty.secuirtymanagement.service;

import java.util.List;
import java.util.Set;

import com.secuirty.secuirtymanagement.model.user;
import com.secuirty.secuirtymanagement.model.userRole;

public interface userService {
    
    public user addUser(user User,Set<userRole> roles);

    public user getUser(long id);
    
    public void deleteUser(long id);


    public List<user> getAllUsers();
}
