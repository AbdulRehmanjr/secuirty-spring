package com.secuirty.secuirtymanagement.database;

import com.secuirty.secuirtymanagement.model.user;

import org.springframework.data.jpa.repository.JpaRepository;




public interface userRepository  extends JpaRepository<user, Long> {

    
    public user findByUserName(String username);
}
