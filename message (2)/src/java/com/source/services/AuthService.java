

package com.source.services;

import com.Manoj.framework.security.SessionUser;
import com.source.dto.UserWithPrivilege;
import com.source.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    public static UserWithPrivilege getLoggedInDetails() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if(authentication==null || !(authentication.getPrincipal() instanceof SessionUser)) {
            return null;
        } else {
            UserWithPrivilege loggedInDetails = ((SessionUser)authentication.getPrincipal()).getModel();
            return loggedInDetails;
        }
    }

    public static User getLoggedInEmployee() {
        UserWithPrivilege details = getLoggedInDetails();
        if(details == null)
            return null;
        
        return details.getUsers();
    }
}
