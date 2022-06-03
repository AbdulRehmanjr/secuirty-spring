package com.secuirty.secuirtymanagement.database;

import com.secuirty.secuirtymanagement.model.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface roleRepository extends JpaRepository<role,Long>{
    
    public role findByRoleName(String roleName);
}
