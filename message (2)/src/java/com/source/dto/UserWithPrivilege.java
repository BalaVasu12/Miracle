package com.source.dto;

import com.Manoj.framework.AppDto;
import com.source.entities.User;
import java.util.Set;


public class UserWithPrivilege extends AppDto {

    private User users;
    private Set privileges;

    public UserWithPrivilege() {
    }

    public UserWithPrivilege(User user, Set privileges) {
        this.users = user;
        this.privileges = privileges;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User user) {
        this.users = user;
    }

    public Set getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set privileges) {
        this.privileges = privileges;
    }
}
