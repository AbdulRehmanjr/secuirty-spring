package com.secuirty.secuirtymanagement.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;



@Entity
@Table(name = "USERROLETABLE")
public class userRole implements GrantedAuthority{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userRoleId;
    @ManyToOne(fetch = FetchType.EAGER)
    private user User;

    @ManyToOne
    private role Role;

    @Override
    public String toString() {
        return "userRole [Role=" + Role + ", User=" + User + ", userRoleId=" + userRoleId + "]";
    }

    public user getUser() {
        return User;
    }

    public void setUser(user user) {
        User = user;
    }

    public role getRole() {
        return Role;
    }

    public void setRole(role role) {
        Role = role;
    }

    @Override
    public String getAuthority() {
       
        return this.getRole().getRoleName();
    }
}
