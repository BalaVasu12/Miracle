package com.source.entities;

import com.Manoj.framework.AppModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "rolePrivileges")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class RolePrivileges extends AppModel{
    
    @Id
    @GeneratedValue
    
    @Column(name = "id")
    long id;
    
    @Column(name = "PrivilegeName")
    String privilegeName;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @NotFound(action=NotFoundAction.EXCEPTION)
    @JoinColumn(name="RoleId", referencedColumnName = "id", insertable = true, updatable = true, nullable=false)
    @JsonIgnore
    Roles role;

    @Override
    public long getId() {
        return id;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public Roles getRole() {
        return role;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
