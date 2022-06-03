package com.secuirty.secuirtymanagement.service;

import java.util.List;

import com.secuirty.secuirtymanagement.model.role;

public interface roleService {
    
    public role addRole(role Role);

    public role getRole(long id);

    public void deleteRole(long id);

    public List<role> getAllRoles();
    
}
