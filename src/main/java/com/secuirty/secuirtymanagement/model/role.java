package com.secuirty.secuirtymanagement.model;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ROLETABLE")
public class role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;
    private String roleName;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "Role")
    @JsonIgnore
    private Set<userRole> userRoles = new HashSet<>();

    public long getRoleId() {
        return roleId;
    }
    public Set<userRole> getUserRoles() {
        return userRoles;
    }
    public void setUserRoles(Set<userRole> userRoles) {
        this.userRoles = userRoles;
    }
    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    @Override
    public String toString() {
        return "role [roleId=" + roleId + ", roleName=" + roleName  + "]";
    }
 

}
