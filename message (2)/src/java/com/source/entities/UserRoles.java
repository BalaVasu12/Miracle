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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "userroles")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRoles extends AppModel{
    
    @Id
    @GeneratedValue
    
    @Column(name = "id")
    long id;
    
    @ManyToOne()
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name = "userid", referencedColumnName="id", insertable=true, updatable=true)
    @JsonIgnore
    User user;
    
    @ManyToOne()
    @NotFound(action=NotFoundAction.EXCEPTION)
    @JoinColumn(name="roleid", referencedColumnName = "id", insertable = true, updatable = true, nullable=true)
    Roles roles;

    @Override
    public long getId() {
        return id;
    }
    

    public Roles getRoles() {
        return roles;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    
    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
