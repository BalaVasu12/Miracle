

package com.Manoj.framework.security;

import com.source.dto.UserWithPrivilege;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class SessionUser extends User {
    
    UserWithPrivilege model; //WRONG practice

    public SessionUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public SessionUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
    
    public SessionUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, UserWithPrivilege model, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.model = model;
    }

    public UserWithPrivilege getModel() {
        return model;
    }

    public void setModel(UserWithPrivilege model) {
        this.model = model;
    }
}
