package com.source.entities;

import com.Manoj.framework.AppModel;
import com.mysql.jdbc.Blob;
import static java.sql.JDBCType.BLOB;
import static java.sql.Types.BLOB;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import static org.hibernate.type.StandardBasicTypes.BLOB;


@Entity
@Table(name="user")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends AppModel{
    
    @Id
    @GeneratedValue
    
    @Column(name="id")
    long id;

    @Column(name="name")
    String name;
    
    @Column(name="username")
    String userName;
    
    @Column(name="password")
    String password;
    
    @Column(name="role")
    String role;
    
    @Column (name="profile")
    String profile; 
    
    @Column (name="address")
    String address;
    
    @Column(name="mobilenumber")
    String mobilenumber;
    
    @Column(name="email")
    String email;
    
//    @JsonIgnore
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, targetEntity = UserRoles.class, cascade = CascadeType.ALL, orphanRemoval = true)
//    List<UserRoles> roles;
    
//    @ManyToOne(fetch = FetchType.LAZY)
//    @NotFound(action = NotFoundAction.EXCEPTION)
//    @JoinColumn(name = "userId", referencedColumnName = "ID", insertable = true, updatable = true, nullable = true)
//    @JsonIgnore
//    User lastmodifiedby;
    
    @Column(name="isActive")
    boolean isActive;
    
    @Column(name="createdOn")
    Date createdOn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

//    public List<UserRoles> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(List<UserRoles> roles) {
//        this.roles = roles;
//    }
//
//    public User getLastmodifiedby() {
//        return lastmodifiedby;
//    }
//
//    public void setLastmodifiedby(User lastmodifiedby) {
//        this.lastmodifiedby = lastmodifiedby;
//    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
