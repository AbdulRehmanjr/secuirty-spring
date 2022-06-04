package com.secuirty.secuirtymanagement.service.implement;

import com.secuirty.secuirtymanagement.model.user;

import com.secuirty.secuirtymanagement.database.userRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class userDetailServiceimpl implements UserDetailsService{
    
    private Logger logger = LoggerFactory.getLogger(userDetailServiceimpl.class);

    @Autowired
    private userRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        user resultUser = this.userRepo.findByUserName(username);

        if(resultUser == null){
            logger.error("User not found");
            return null;
        }
        return resultUser;
    }


}
