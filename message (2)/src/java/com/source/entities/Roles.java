package com.source.entities;

import com.Manoj.framework.AppModel;
import com.Manoj.framework.search.annotations.AutoSearch;
import com.Manoj.framework.search.annotations.AutoSearchType;
import com.Manoj.framework.search.types.LikeSearch;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@AutoSearch
@Table(name = "roles")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Roles extends AppModel{
  
    @Id
    @GeneratedValue
    
    @Column(name = "id")
    long id;
    
    @Column(name = "RoleName")
    @AutoSearchType(type=LikeSearch.class)        
    String roleName;
    
    @OneToMany(mappedBy="role", fetch = FetchType.LAZY, targetEntity = RolePrivileges.class)
    List<RolePrivileges> privileges;

    @Override
    public long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<RolePrivileges> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<RolePrivileges> privileges) {
        this.privileges = privileges;
    }
}
