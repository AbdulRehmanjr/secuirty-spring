package com.secuirty.secuirtymanagement.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;





@Entity
@Table(name = "USERTABLE")
public class user implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String userName;
    private String userPassword;
    private boolean enabled = true;
    
    
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "User")
    @JsonIgnore
    private Set<userRole> userRoles = new HashSet<>();
    
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
  
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public Set<userRole> getUserRoles() {
        return userRoles;
    }
    public void setUserRoles(Set<userRole> userRoles) {
        this.userRoles = userRoles;
    }

 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        Set<authority> auth = new HashSet<>();
        this.userRoles.forEach(userRole -> {
            auth.add(new authority(userRole.getRole().getRoleName()));
        }
        );
        
       
        return auth;
    }
    @Override
    public boolean isAccountNonExpired() {
        
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        
        return true;
    }
    @Override
    public String toString() {
        return "user [email=" + email + ", enabled=" + enabled + ", firstName=" + firstName + ", lastName=" + lastName
                + ", phone=" + phone + ", userId=" + userId + ", userName=" + userName + ", userPassword="
                + userPassword + ", userRoles=" + userRoles + "]";
    }
    @Override
    public String getPassword() {
        
        return this.getUserName();
    }
    @Override
    public String getUsername() {
        
        return this.getUserPassword();
    }

  
}
