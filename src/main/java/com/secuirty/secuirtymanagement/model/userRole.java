package com.secuirty.secuirtymanagement.model;



import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
@Table(name = "USERROLETABLE")
public class userRole  {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userRoleId;
    @ManyToOne(fetch = FetchType.EAGER)
    private user User;

    @ManyToOne
    private role Role;


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

    public long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(long userRoleId) {
        this.userRoleId = userRoleId;
    }

    @Override
    public String toString() {
        return "userRole [Role=" + Role + "]";
    }


 
}
