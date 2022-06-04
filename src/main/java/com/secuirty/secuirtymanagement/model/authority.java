package com.secuirty.secuirtymanagement.model;

import org.springframework.security.core.GrantedAuthority;

public class authority implements GrantedAuthority {

    private String authority;

    public authority(String authority) {
        this.authority = authority;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
      
        return this.authority;
    }


    
  
}
